package vazkii.patchouli.client.book.text;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.common.book.Book;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SpanState {
	public final GuiBook gui;
	public final Book book;

	private final Style baseStyle;
	private final Deque<Style> styleStack = new ArrayDeque<>();
	public IFormattableTextComponent tooltip = BookTextParser.EMPTY_STRING_COMPONENT;
	public Supplier<Boolean> onClick = null;
	public List<Span> cluster = null;
	public boolean isExternalLink = false; // will show the "external link" symbol next to the link as soon as the link is closed
	public boolean endingExternal = false; // will show the "external link" symbol next to the link immediately
	public int lineBreaks = 0; // force line breaks
	public int spacingLeft = 0; // add extra spacing
	public int spacingRight = 0;
	public final int spaceWidth;

	public SpanState(GuiBook gui, Book book, Style baseStyle) {
		this.gui = gui;
		this.book = book;
		this.baseStyle = baseStyle;
		this.styleStack.push(baseStyle);
		this.spaceWidth = Minecraft.getInstance().fontRenderer.getStringPropertyWidth(new StringTextComponent(" ").setStyle(baseStyle));
	}

	public Style getBase() {
		return baseStyle;
	}

	public String color(Color color) {
		return modifyStyle(s -> s.setColor(color));
	}

	public String baseColor() {
		return color(baseStyle.getColor());
	}

	public String modifyStyle(Function<Style, Style> f) {
		Style top = styleStack.pop();
		styleStack.push(f.apply(top));
		return "";
	}

	public void pushStyle(Style style) {
		Style top = styleStack.peek();
		styleStack.push(style.mergeStyle(top));
	}

	public Style popStyle() {
		if (styleStack.size() <= 1) {
			throw new IllegalStateException("Underflow in style stack");
		}
		Style ret = styleStack.pop();
		return ret;
	}

	public void reset() {
		endingExternal = isExternalLink;
		styleStack.clear();
		styleStack.push(baseStyle);
		cluster = null;
		tooltip = BookTextParser.EMPTY_STRING_COMPONENT;
		onClick = null;
		isExternalLink = false;
	}

	public Style peekStyle() {
		return styleStack.getFirst();
	}
}
