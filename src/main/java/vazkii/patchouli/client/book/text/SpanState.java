package vazkii.patchouli.client.book.text;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;

import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.common.book.Book;

import java.util.List;
import java.util.function.Supplier;

public class SpanState {
	public final GuiBook gui;
	public final Book book;
	public final FontRenderer font;
	public final int baseColor;

	public int color;
	public int prevColor;
	public String codes = "";
	public ITextComponent tooltip = BookTextParser.EMPTY_STRING_COMPONENT;
	public Supplier<Boolean> onClick = null;
	public List<Span> cluster = null;
	public boolean isExternalLink = false; // will show the "external link" symbol next to the link as soon as the link is closed
	public boolean endingExternal = false; // will show the "external link" symbol next to the link immediately
	public int lineBreaks = 0; // force line breaks
	public int spacingLeft = 0; // add extra spacing
	public int spacingRight = 0;

	public SpanState(GuiBook gui, Book book, int baseColor, FontRenderer font) {
		this.gui = gui;
		this.book = book;
		this.baseColor = baseColor;
		this.font = font;

		this.color = baseColor;
		this.prevColor = baseColor;
	}

	public String codes(String codes) {
		this.codes = codes;
		return "";
	}

	public String color(int color) {
		this.color = color;
		return "";
	}

	public void reset() {
		endingExternal = isExternalLink;
		color = baseColor;
		codes = "";
		cluster = null;
		tooltip = BookTextParser.EMPTY_STRING_COMPONENT;
		onClick = null;
		isExternalLink = false;
	}
}
