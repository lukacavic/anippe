package com.velebit.anippe.client.documents;

import org.eclipse.scout.rt.client.ui.tile.AbstractHtmlTile;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class DocumentTile extends AbstractHtmlTile {

    @Override
    public String getContent() {
        String title = "mojaslika.png";
        String createdAt = new PrettyTime().format(new Date());
        String category = "15KB";
        String description = "Moj dokument opis..";
        String creator = "Luka Čavić";

        IHtmlContent content = HTML.fragment(
                HTML.div(
                        HTML.bold(title).style("font-size:15px;color:#234d74;"),
                        HTML.span(category).style("font-size:9px;"),
                        HTML.img("https://www.apartmani-villa-bose.com/foto/7/1.jpg").addAttribute("width", "200").addAttribute("height", "150").style("overflow:hidden; margin-top:5px;margin-bottom:5px;border:2px solid #234d74;border-radius:3px;"),
                        HTML.italic(creator, HTML.span(", "), createdAt).style("font-size:12px;")
                ).style("height:100%;display:flex;flex-direction: column;justify-content: space-between;")
        );

        return content.toHtml();
    }
}
