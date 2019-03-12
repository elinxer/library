# encoding: utf-8

import requests
import json
from urllib import quote
import time
import urlparse
import re
import traceback

start_time = time.time() - 1382400
s = requests.session()
headers = {
    'User-Agent': 'Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1'}
data = {
    'is_only_read': 1,
    'req_id': '2816y9SXfWdmII2i5RdVm1CE',
    'pass_ticket': None,
    'is_temp_url': 0}
KEY = ''
UIN = ''


def getarg():
    global KEY, UIN
    f = open('1.txt')
    arg = [line for line in f]
    f.close()
    url = arg[0].strip()
    query = urlparse.urlparse(url).query
    args = dict([(k, v[0]) for k, v in urlparse.parse_qs(query).items()])
    KEY = args['key'].strip()
    UIN = args['uin'].strip()


def get_article(fakeid):
    begin = 0
    while 1:
        url = 'https://mp.weixin.qq.com/mp/profile_ext?action=getmsg&__biz=%s&f=json&offset=%s&count=10&uin=%s&key=%s' % (
            quote(fakeid), begin, UIN, KEY)
        print
        url
        try:
            res = s.get(url, headers=headers)
        except:
            continue

        data = res.json()
        data = json.loads(data['general_msg_list'])
        if not len(data['list']):
            break
        for item in data['list']:
            post_date = int(item['comm_msg_info']['datetime'])
            if post_date < int(time.mktime(time.strptime(time.strftime('%Y-%m-%d'), '%Y-%m-%d'))):
                return
            if item['comm_msg_info']['type'] in (1, 3, 34):
                return
            title = item['app_msg_ext_info']['title']
            post_url = item['app_msg_ext_info']['content_url']
            yield {
                'post_date': post_date,
                'post_title': title,
                'post_url': post_url}

            for multi in item.get('app_msg_ext_info', {
                'multi_app_msg_item_list': {}}).get('multi_app_msg_item_list', []):
                title = multi['title']
                post_url = multi['content_url']
                yield {
                    'post_date': post_date,
                    'post_title': title,
                    'post_url': post_url}

        begin += 10


def get_cookie(biz):
    url = 'https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz={}&uin={}&key={}'.format(biz, UIN, KEY)
    print
    url
    while 1:

        try:
            print
            s.get(url, headers=headers).headers
            break
        except:
            continue


def get_token(url):
    page = s.get(url, headers=headers).content
    token = re.findall('window.appmsg_token = "([^"]+)', page)[0]
    return token


def get_likes(url):
    query = urlparse.urlparse(url).query
    args = dict([(k, v[0]) for k, v in urlparse.parse_qs(query).items()])

    try:
        biz = args['__biz']
        mid = args['mid']
        idx = args['idx']
        sn = args['sn']
    except:
        return ('N/A', 'N/A')

    get_cookie(biz)
    token = get_token(url)
    try:
        page = s.get(url).content
        comment_id = re.findall('comment_id = "([\\d]+)', page)[0]
        comm_url = 'https://mp.weixin.qq.com/mp/appmsg_comment?action=getcomment&scene=0&__biz=%s&comment_id=%s&offset=0&limit=1&appmsg_token=%s&f=json' % (
            biz, comment_id, token)
        res = s.get(comm_url).json()
        comm_num = res['elected_comment_total_cnt']
    except:

        comm_num = None

    like_url = 'https://mp.weixin.qq.com/mp/getappmsgext?__biz=%s&mid=%s&sn=%s&idx=%s&appmsg_token=%s' % (
        biz, mid, sn, idx, token)
    print
    like_url
    while 1:

        try:
            res = s.post(like_url, headers=headers, data=data)
            break
        except:
            time.sleep(0.5)
            continue

    result = res.json()
    print
    result
    like_num = result['appmsgstat']['like_num']
    read_num = result['appmsgstat']['read_num']
    return (like_num, read_num, comm_num)


def getart(dic):
    for item in dic:
        fakeid = item['biz']
        k = item['name']
        # id = item['id']
        getarg()
        get_cookie(fakeid)
        for each in get_article(fakeid):
            post_url = each['post_url']
            title = each['post_title']
            print
            title
            num = get_likes(post_url)
            page_views = num[1]
            likes = num[0]
            comm_num = num[2]
            post_date = each['post_date']
            print
            title, page_views, likes, comm_num
            url = 'http://spider.huazhen2008.com/api/weixin_article?key=huazjojojowe3451123&title=%s&post_date=%s&spider_at=%s&url=%s&read_num=%s&likes=%s&weixin_mp=%s&comment_num=%s' % (
                quote(title.encode('utf-8')), post_date, int(time.time()), quote(post_url), page_views, likes,
                quote(k.encode('utf-8')), comm_num)
            print
            url
            res = requests.get(url).json()

            try:
                print
                res['code'], res['data']['post_at'], res['data']['spider_at']
            except:
                pass

            time.sleep(3)


def getlike(li):
    getarg()
    for item in li:
        id = item['id']
        url = item['url']
        num = get_likes(url)
        page_views = num[1]
        likes = num[0]
        comm_num = num[2]

        try:
            url = 'http://spider.huazhen2008.com/api/weixin_article?key=huazjojojowe3451123&spider_at=%s&url=%s&read_num=%s&likes=%s&comment_num=%s&weixin_mp_art_id=%s' % (
                int(time.time()), quote(url), page_views, likes, comm_num, id)
        except Exception as e:
            print
            traceback.format_exc(e)

        print
        url

        try:
            res = requests.get(url)
            res = res.json()
            print
            res['code']
        except:
            pass

        time.sleep(3)


if __name__ == '__main__':
    getart([{u'status': 1, u'mp_key': u'Miss-luola', u'spider_time': [u'16:10:00', u'23:55:00'],
             u'name': u'\u7f57\u62c9\u5c0f\u59d0', u'biz': u'MzIwMzQxNzIwOA==', u'id': 140,
             u'spider_time_one': u'16:10:00'}])
    # getarg()
    # get_likes('https://mp.weixin.qq.com/s?__biz=MzI4MTI5NTE3MQ==&mid=2247492597&idx=4&sn=374973fad0e8d2c570fb39acba6b6888&chksm=eba9c3d9dcde4acf9ee8299e13c6b0f6bcf479244dbc3479f6b44e05d0239ea84fedf6173598&scene=38#wechat_redirect')
    # getarg()
    # get_cookie('MjM5NTk4OTU5Mw==')
    # print s.cookies
    # url='https://mp.weixin.qq.com/mp/getappmsgext?__biz=MjM5NTk4OTU5Mw==&mid=2650325088&sn=397a40b16da8d6a20401d4cca6431b88&idx=1&f=json&appmsg_token=924_lEynRXQ%252BdVRSzvoWsVxiSST53nRoWKRHM6eMs7W7WunbBDyO1z7Ylil9DYVk0SJio-WELtqwMwrVvvuJ'
    # print s.post(url,headers=headers,data=data).url
