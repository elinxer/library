import scrapy
import json

class UcRankSpider(scrapy.Spider):
    name = 'uc_rank'
    allowed_domains = ['m.sm.cn']
    start_urls = [
        'https://m.sm.cn/api/rest?method=Novelnew.home&format=html&schema=v2&rank=rank_hot&uc_param_str=dnntnwvepffrbijbprsvchgputdemennosstodcaaagidseilo&from=ucframe&gender=male&cate=%E4%BB%99%E4%BE%A0',

    ]

    def parse(self, response):

        # content = json.loads(response.body_as_unicode())

        content = response.css(".c-container .c-list-container-wrapper")
        items = content.css(".c-list-container-wrapper .c-list-container-item")
        for item in items:
            book_name = item.css(".dl-container .c-header-title span::text").getall()
            book_intro = item.css(".novel-desc::text").get()
            book_author = item.css(".js-c-clamp-wrapper .js-c-paragraph-text::text").get()
            book_url = item.css(".js-c-clamp-wrapper .js-c-paragraph-text::text").get()
            search_count = item.css(".js-c-clamp-wrapper .js-c-paragraph-text .search-count::text").get()
            if len(book_name) == 2:
                book_name = book_name[0] + "." + book_name[1]
            else:
                book_name = book_name[0]
            book_name = book_name.split(".")
            book_rank = book_name[0]
            book_name = book_name[1]

            print(book_name)
        pass
