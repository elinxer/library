package main

import (
	"fmt"
	"github.com/antchfx/htmlquery"

	//"github.com/jackdanger/collectlinks"
	"log"
	"net/http"
	"net/url"
	"strings"
	"time"
)

var visited = make(map[string]bool)

func main() {

	url := "http://www.mafengwo.cn/mdd/ajax_photolist.php?act=getMddPhotoList&mddid=12503&page=1"

	queue := make(chan string, )
	go func() {
		queue <- url
	}()

	for uri := range queue {
		download(uri, queue)
	}
}

func download(url string, queue chan string) {
	visited[url] = true
	timeout := time.Duration(5 * time.Second)

	client := &http.Client{
		Timeout:timeout,
	}
	req, _ := http.NewRequest("GET", url, nil)
	// 自定义Header
	req.Header.Set("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)")

	resp, err := client.Do(req)
	if err != nil {
		fmt.Println("http get error", err)
		return
	}
	//函数结束后关闭相关链接
	defer resp.Body.Close()

	doc, err := htmlquery.Parse(resp.Body)
	if err != nil{
		log.Printf("htmlquery parse err :", err)
	}
	// <a href="https://xxxx.org/sss.html" target="_blank">唐诗三百</a>
	for _, n := range htmlquery.Find(doc, "/img") {
		// 获取href中的链接
		fmt.Printf("%s\n", htmlquery.SelectAttr(n, "href"))
		// 获取a标签中的text值
		fmt.Printf("%s\n", htmlquery.OutputHTML(n, false))
	}

	//ParseTagList(resp.Body)


	//
	//links := collectlinks.All(resp.Body)

	//for _, link := range links {
	//	absolute := urlJoin(link, url)
	//	if url != " " {
	//		if !visited[absolute] {
	//			fmt.Println("parse url", absolute)
	//			go func() {
	//				queue <- absolute
	//			}()
	//		}
	//	}
	//}
}

func urlJoin(href, base string) string {
	uri, err := url.Parse(href)
	if err != nil {
		return " "
	}
	baseUrl, err := url.Parse(base)
	if err != nil {
		return " "
	}
	return baseUrl.ResolveReference(uri).String()
}


func ParseTagList(contents []byte) {
	// 解析URL
	// doc, err := htmlquery.LoadURL("http://example.com/")
	// 解析html
	doc, err := htmlquery.Parse(strings.NewReader(string(contents)))
	if err != nil{
		log.Printf("htmlquery parse err :", err)
	}
	// <a href="https://xxxx.org/sss.html" target="_blank">唐诗三百</a>
	for _, n := range htmlquery.Find(doc, "/img") {
		// 获取href中的链接
		fmt.Printf("%s\n", htmlquery.SelectAttr(n, "href"))
		// 获取a标签中的text值
		fmt.Printf("%s\n", htmlquery.OutputHTML(n, false))
	}
}
