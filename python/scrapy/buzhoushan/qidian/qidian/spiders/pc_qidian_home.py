import scrapy
import json
from ..items import QidianPCHomeBookUrlItem, QidianBookDetailItem


class PcQidianHomeSpider(scrapy.Spider):
    name = 'pc_qidian_home'
    allowed_domains = ['www.qidian.com']
    start_urls = [
        'https://www.qidian.com/', 'https://www.qidian.com/xianxia/', 'https://www.qidian.com/kehuan/',
        'https://www.qidian.com/dushi/', 'https://www.qidian.com/youxi/', 'https://www.qidian.com/lishi/'
    ]
    unique_urls = []

    def detail_parse(self, response):
        items = QidianBookDetailItem()

        content = response.css('.book-detail-wrap')
        book_name = content.css(".book-info h1 em::text").get()
        book_author = content.css(".book-info h1 span a::text").get()
        book_img = content.css("#bookImg img::attr(src)").get().replace("\n", "").replace("  ", "")
        book_intro = content.css(".book-intro p::text").get().replace("\n", "").replace("  ", "").replace("\u3000", "")
        book_url = response.request.url

        book_tags_span = content.css(".book-info .tag span::text").getall()
        book_tags_a = content.css(".book-info .tag a::text").getall()

        book_tags = book_tags_span + book_tags_a
        book_status = 0
        if "完本" in book_tags:
            book_status = 2
        elif "连载" in book_tags:
            book_status = 1

        items['book_name'] = book_name
        items['book_author'] = book_author
        items['book_img'] = "https:" + book_img
        items['book_url'] = book_url
        items['book_intro'] = book_intro
        items['book_tags'] = json.dumps(book_tags, ensure_ascii=False)
        items['book_status'] = book_status
        yield items
        pass

    def parse(self, response):
        items = QidianPCHomeBookUrlItem()
        for quote in response.xpath('//a/@href'):
            str = "book.qidian.com/info"
            if str not in quote.get():
                continue
            book_url = quote.get()
            if book_url.find("https://") == -1:
                book_url = "https:" + book_url
            if book_url in self.unique_urls:
                continue
            self.unique_urls.append(book_url)
            items["book_url"] = book_url
            #yield items
        for url in self.unique_urls:
            yield scrapy.Request(url, callback=self.detail_parse, dont_filter=True)
            pass
        pass
