package com.velebit.anippe.client.documents;

import org.eclipse.scout.rt.client.ui.tile.AbstractHtmlTile;
import org.eclipse.scout.rt.platform.html.HTML;

public class DocumentTile extends AbstractHtmlTile {

    @Override
    public String getContent() {
        return HTML.fragment(HTML.span("ovo je dokument")).toHtml();
    }
}
