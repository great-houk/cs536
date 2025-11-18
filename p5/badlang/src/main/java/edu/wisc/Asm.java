package edu.wisc;

/** Minimal assembler emitter: collects .data/.text and generates labels. */
public final class Asm {
    private final StringBuilder data = new StringBuilder(".data\n");
    private final StringBuilder text = new StringBuilder();
    private int id = 0;

    /** Emit a line into the .data section. */
    public void d(String line) { data.append(line).append('\n'); }

    /** Emit a line into the .text section. */
    public void t(String line) { text.append(line).append('\n'); }

    /** Emit a text label like "name:" on its own line. */
    public void label(String name) { text.append(name).append(":\n"); }

    /** Generate a unique label name: base_#, e.g., "if_3". */
    public String newLabel(String base) { return base + "_" + (id++); }

    /** Get the full assembly text to write to a file. */
    public String emit() { return data.toString() + "\n" + text.toString(); }
}
