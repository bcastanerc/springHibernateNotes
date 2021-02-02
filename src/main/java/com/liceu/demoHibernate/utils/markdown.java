package com.liceu.demoHibernate.utils;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class markdown {
    /**
     * Parse the note with markdown to html.
     * @param text text of the note.
     * @return String parsed to html.
     */
    public static String renderNote(String text){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(text);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
