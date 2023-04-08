# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter

import pymysql
from .items import QidianBookDetailItem, QidianPCHomeBookUrlItem


class QidianPipeline:

    def __init__(self):
        self.conn = pymysql.connect(host="47.119.145.8", user="root", password="#p?Z#x+eq4jp", database="buzhoushan",
                                    charset="utf8", use_unicode=True)
        self.cursor = self.conn.cursor()

    def process_item(self, item, spider):

        if isinstance(item, QidianBookDetailItem):
            insert_sql = """
                        insert ignore into book_url_spider(name, author, img, url, intro, tags, source, status)
                        VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
                    """
            self.cursor.execute(insert_sql, (
                item["book_name"], item["book_author"], item["book_img"], item["book_url"], item["book_intro"],
                item["book_tags"], "qidian", item["book_status"]
            ))
            self.conn.commit()
            return item

        if isinstance(item, QidianPCHomeBookUrlItem):
            # print(item)
            return item
        pass
