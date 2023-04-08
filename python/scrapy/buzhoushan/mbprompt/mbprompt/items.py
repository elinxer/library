# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class MbpromptItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()

    name = scrapy.Field()
    pic = scrapy.Field()
    desc = scrapy.Field()
    topic_name = scrapy.Field()
    topic_id = scrapy.Field()
    gallery_id = scrapy.Field()

    pass
