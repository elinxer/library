# coding:utf-8

import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web
import traceback
import tornado.httpclient
import tornado.gen
import requests
from tornado.concurrent import run_on_executor
from concurrent.futures import ThreadPoolExecutor
import json
import time
import urlparse
from tornado.options import define, options
from getlike import getart, getlike

define("port", default=8000, type=int)
headers = {
    'Host': 'mp.weixin.qq.com',
    'Origin': 'https://mp.weixin.qq.com',
    'User-Agent': 'Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1',
}
data = {
    'is_only_read': 1,
    'req_id': '2515lQuLQfC4QV5VH7j0aZYR',
    'pass_ticket': None,
    'is_temp_url': 0
}
s = requests.Session()


class ApiHandler(tornado.web.RequestHandler):
    executor = ThreadPoolExecutor(2)

    @tornado.web.asynchronous
    @tornado.gen.coroutine
    def post(self, *args, **kwargs):
        try:
            data = json.loads(self.request.body)
            print(data)
        except:
            message = {'code': 400, 'message': u'请Post json格式数据'}
            self.write(message)
            self.finish()
        if data['type'] == 'mp':
            for item in data['data']:
                try:
                    id = item['id']
                    name = item['name']
                    biz = item['biz']
                except:
                    message = {'code': 400, "message": u'参数错误', 'content': item}
                    self.finish()
            self.get_list(data['data'])
        elif data['type'] == 'article':
            for item in data['data']:
                try:
                    url = item['url']
                    query = urlparse.urlparse(url).query
                    args = dict([(k, v[0]) for k, v in urlparse.parse_qs(query).items()])
                    biz = args['__biz']
                    mid = args['mid']
                    idx = args['idx']
                    sn = args['sn']
                except Exception as e:
                    print
                    traceback.format_exc(e)
                    message = {'code': 400, "message": u'参数错误', 'content': item}
                    self.write(message)
                    self.finish()
                    break
            self.get_likes(data['data'])
        else:
            message = {'code': '400', "message": u'type 错误'}

        try:
            self.write({'code': 200, 'message': 'OK'})
        except:
            pass

    @run_on_executor
    def get_list(self, dic):
        getart(dic)

    @run_on_executor
    def get_likes(self, list):
        getlike(list)


if __name__ == "__main__":
    tornado.options.parse_command_line()
    app = tornado.web.Application(handlers=[
        (r"/api", ApiHandler)])
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()
