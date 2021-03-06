package ru.nojs.rest.bitcoin;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.nojs.vk.VkService;
import ru.nojs.vk.VkUser;
import ru.nojs.vk.VkUsers;
import ru.vdovin.rest.bitcoin.WebTargetImpl;


public class BitcoinRatesServiceTest {
    static WebTarget wt;
    static BitcoinRatesService ratesService;

    @BeforeClass
    public static void init() {
        wt = new WebTargetImpl("https://api.bitcoinaverage.com/");
        ratesService = wt.proxy(BitcoinRatesService.class);
        /*ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("https://api.bitcoinaverage.com/");
        ratesService = target.proxy(BitcoinRatesService.class);*/
    }

    @Test
    public void testProxyCreation() throws Exception {
        Assert.assertNotNull("Proxy shouldn't be null", ratesService);
    }

    @Test
    public void testListAll() throws Exception {
        Tickers tickers = ratesService.listAll();
        Assert.assertTrue("We've got some list", tickers.tickers().size() > 5);
        String pathToRUB = tickers.tickers().get("RUB");
        Assert.assertNotNull("There is RUB ticker", pathToRUB);
    }

    @Test
    public void testGet() throws Exception {
        Tickers tickers = ratesService.listAll();
        Ticker rubTicker = ratesService.get("LVL");
        Assert.assertTrue("We've got some list", tickers.tickers().size() > 150);
        Assert.assertNotNull("Ask fields is set", rubTicker.ask);
        Assert.assertNotNull("Bid fields is set", rubTicker.bid);
        Assert.assertNotNull("Last fields is set", rubTicker.last);
        Assert.assertNotNull("Timestamp fields is set", rubTicker.timestamp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailUriGet() {
        Ticker rubTicker = ratesService.get("NORUB");
    }

    @Test
    public void testVkUserGet() throws Exception  {

        WebTarget vkWt = new WebTargetImpl("https://api.vk.com/");
        VkService vkService = vkWt.proxy(VkService.class);

        VkUsers users = vkService.getInfo("vdovin_d_s");
        Assert.assertNotNull("We've got users", users);
        VkUser user = users.users().get(0);

        Assert.assertTrue("First name is correct", user.getFirstName().equals("Дмитрий"));
        Assert.assertTrue("Last name is correct", user.getLastName().equals("Вдовин"));

    }

}