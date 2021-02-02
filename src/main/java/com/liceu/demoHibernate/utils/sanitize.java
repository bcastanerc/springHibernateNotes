package com.liceu.demoHibernate.utils;


import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class sanitize {
    /**
     * Esta función pasa de texto markdown a html y de el html resultante a texto plano.
     * @param text texto escrito por el usuario en la nota.
     * @return el texto saneado sin markdown ni html.
     */
    public static String sanitizeNote(String text){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(text);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
        String sanitizedText = policy.sanitize(renderer.render(document));
        return sanitizedText;
    }

}
