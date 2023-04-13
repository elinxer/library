# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


# useful for handling different item types with a single interface
from itemadapter import ItemAdapter
import os
import requests
import json


class LexicaArtPipeline:

    # 接口内容完整保存

    # 图片关系保存

    # 图片下载

    def download_image(image_url, storage_path):
        headers = {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36'
        }
        img_path = image_url.split('/')[-1]
        img_prefix = img_path.split('.')[-1]
        img_name = img_path.split('.')[-2]
        print(img_path, img_name, img_prefix)
        new_path = storage_path + img_name + "." + img_prefix
        if not os.path.exists(storage_path):
            os.mkdir(storage_path)
        r = requests.get(image_url, headers=headers)
        # download image
        with open(new_path, mode="wb") as f:
            f.write(r.content)
        print("====> download success: " + new_path)
        pass

    def save_relation(storage, body):
        img_path = storage + body['pic'].split('/')[-1]
        print(img_path)
        if not os.path.exists(storage):
            os.mkdir(storage)
        # mode中"a"是追加模式，"r"是只读模式，默认“w”是重写模式
        f = open(storage, mode="a")
        f.write(body['name'] + " | " + img_path + " | " + body['pic'] + "\n")
        pass

    def save_api(self, storage, item):
        # if not os.path.exists(storage):
        # os.mkdir(storage)
        # mode中"a"是追加模式，"r"是只读模式，默认“w”是重写模式
        f = open(storage, mode="a")
        f.write(str(item) + "\n||\n")
        pass

    def process_item(self, item, spider):
        self.save_api("api.txt", item)
        return item
