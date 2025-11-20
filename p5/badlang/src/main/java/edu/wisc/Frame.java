package edu.wisc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Frame {
	private final int frameSize;
	private final Map<String, Integer> offsets = new HashMap<>(); // var/param name -> offset

	private Frame(int frameSize) {
		this.frameSize = frameSize;
	}

	public static Frame build(Stmt.Function f) {
		// Simple, flat allocation:
		// Layout (callee after prologue, $fp set):
		//   ($fp + 8)   = 1st arg
		//   ($fp + 12)  = 2nd arg ...
		// Locals below $fp: -4, -8, ...
		int localCount = countLocalsFlat(f.body);
		int localBytes = 4 * localCount;

		int saveArea = 8; // saved $ra, saved $fp
		int tempArea = 0; // start with 0; add later if you want spills
		int frameSize = align4(saveArea + localBytes + tempArea);

		Frame fr = new Frame(frameSize);

		// Param offsets: +8, +12, ...
		int off = 8;
		for (var p : f.params) {
			fr.offsets.put(p.name(), off);
			off += 4;
		}

		// Local offsets: -4, -8, ...
		int locOff = -4;
		assignLocalsFlat(f.body, fr.offsets, locOff);
		return fr;
	}

	public int size() {
		return frameSize;
	}

	/** Returns offset relative to $fp. Negative = local, positive = param. */
	public int offsetOf(String name) {
		Integer o = offsets.get(name);
		if (o == null)
			throw new IllegalStateException("No offset for var '" + name + "'");
		return o;
	}

	// --- helpers: flat scan of function body for Var declarations ---
	private static int countLocalsFlat(java.util.List<Stmt> stmts) {
		int n = 0;
		for (var s : stmts) {
			if (s instanceof Stmt.Var)
				n++;
			else if (s instanceof Stmt.Block b)
				n += countLocalsFlat(b.statements);
			else if (s instanceof Stmt.If i) {
				n += countLocalsFlat(List.of(i.thenBranch));
				if (i.elseBranch != null)
					n += countLocalsFlat(List.of(i.elseBranch));
			} else if (s instanceof Stmt.While w) {
				n += countLocalsFlat(List.of(w.body));
			}
		}
		return n;
	}

	private static void assignLocalsFlat(java.util.List<Stmt> stmts, Map<String, Integer> map, int startOff) {
		int off = startOff;
		for (var s : stmts) {
			if (s instanceof Stmt.Var v) {
				map.put(v.name, off);
				off -= 4;
			} else if (s instanceof Stmt.Block b)
				assignLocalsFlat(b.statements, map, off);
			else if (s instanceof Stmt.If i) {
				assignLocalsFlat(List.of(i.thenBranch), map, off);
				if (i.elseBranch != null)
					assignLocalsFlat(List.of(i.elseBranch), map, off);
			} else if (s instanceof Stmt.While w) {
				assignLocalsFlat(List.of(w.body), map, off);
			}
		}
	}

	private static int align4(int n) {
		return (n + 3) & ~3;
	}
}
