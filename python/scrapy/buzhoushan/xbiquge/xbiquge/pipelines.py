# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter
import pymysql
from .items import XbiqugeItem

class XbiqugePipeline:

    def __init__(self):
        self.conn = pymysql.connect(host="47.119.145.8", user="root", password="#p?Z#x+eq4jp", database="buzhoushan",
                                    charset="utf8", use_unicode=True)
        self.cursor = self.conn.cursor()

    def process_item(self, item, spider):

        if isinstance(item, XbiqugeItem):
            insert_sql = """
                        insert ignore into book_url_spider_xbiquge(name, author, img, url, intro, tags, source, status, lasted_at)
                        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)
                    """
            self.cursor.execute(insert_sql, (
                item["book_name"], item["book_author"], "", item["book_url"], "",
                "", "xbiquge", "1", item["book_lasted"]
            ))
            self.conn.commit()
            return item
        pass

