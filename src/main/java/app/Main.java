package app;

import java.util.List;
import client.SiteHandler;
import model.Site;

public class Main {

    public static void main(String[] args) throws Exception {
        final List<Site> sites = SiteHandler.getInstance().getAllMeliSite();

        for (final Site site: sites) {
            System.out.println(site);
        }
    }
}
