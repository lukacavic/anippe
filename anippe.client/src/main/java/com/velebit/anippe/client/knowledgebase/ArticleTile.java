package com.velebit.anippe.client.knowledgebase;

import com.velebit.anippe.shared.knowledgebase.Article;
import org.eclipse.scout.rt.client.ui.tile.AbstractHtmlTile;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.ocpsoft.prettytime.PrettyTime;

public class ArticleTile extends AbstractHtmlTile {
    private Article article;

    public ArticleTile(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    private String limit(String string) {
        StringBuilder buf = new StringBuilder(string);
        if (buf.length() > 150) {
            buf.setLength(150);
            buf.append("...");
        }

        return buf.toString();
    }

    @Override
    public String getContent() {
        String title = article.getTitle();
        String createdAt = new PrettyTime().format(article.getCreatedAt());
        String article = getArticle().getContent();
       // String creator = getArticle().getUserCreated().getFullName();

        IHtmlContent content = HTML.fragment(
                HTML.div(
                        HTML.bold(title).style("font-size:15px;color:#234d74;"),
                        HTML.span("Fiskalizacija").style("font-size:9px;"),
                        HTML.p(limit(article)).style("flex-grow:1;"),
                        HTML.italic("Luka Čavić", HTML.span(", "),createdAt).style("font-size:12px;")
                ).style("height:100%;display:flex;flex-direction: column;justify-content: space-between;")
        );

        return content.toHtml();
    }
}
