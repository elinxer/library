import os

import requests
from lxml import etree


# Python3爬虫（五）解析库的使用之XPath
# https://www.cnblogs.com/Infi-chu/p/8961513.html

def parse_local(file):
    content = etree.parse(file, etree.HTMLParser())
    response = content.xpath(
        '//div[@class="dark:bg-[#131624] bg-gray-200 rounded-md overflow-hidden cursor-pointer select-none"]')

    itemArr = []
    for html in response:
        arr = {}

        # 这里非常关键，一点是从当前节点开始进行检索
        arr['pic'] = html.xpath(".//img[@class='w-full dark:opacity-80 opacity-90']/@src")
        arr['name'] = html.xpath(".//div[@class='py-1 px-2 text-sm text-gray-500']/text()")

        arr['pic'] = "https://www.imiprompt.com" + arr['pic'][0]
        arr['name'] = arr['name'][0]
        itemArr.append(arr)

    return itemArr


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
    # if not os.path.exists(storage):
    #     os.mkdir(storage)
    # mode中"a"是追加模式，"r"是只读模式，默认“w”是重写模式
    # f = open(storage + "relation.txt", mode="a")
    # f.write(body['name'] + " | " + img_path + " | " + body['pic'] + "\n")

    img_path = "http://imgcdn.zhiteer.com/" + img_path
    f = open("relation.txt", mode="a")
    f.write("themes" + "\t" + body['name'] + "\t" + img_path + "\n")

    pass


file_path = 'file:///D:/develop/buzhous/library/python/scrapy/buzhoushan/imiprompt-offline/imi-html/'

file_layouts = file_path + "imi-themes.html"
storage_path = "imiprompt/themes/"
contentArr = parse_local(file_layouts)

for arr in contentArr:
    save_relation(storage_path, arr)
    # download_image(arr['pic'], storage_path)
