import urllib2
import re
from azure.storage.table import TableService, Entity
from bs4 import BeautifulSoup

base_url = 'http://economictimes.indiatimes.com/news/economy/agriculture'

def extract_news():
	
	bd = urllib2.Request(base_url, headers={'User-Agent' : "Magic Browser"}) 
	req = urllib2.urlopen(bd)
	data = BeautifulSoup(req.read(),"lxml")
	news_list = data.find_all("div", {"class":"eachStory"})
	news_data_list = data.find_all("p", {"class":""})
	news_data_list = news_data_list[3:]
	pattern = re.compile('<p>(.*?)<\/p>', re.DOTALL)
	
	for i in range(0,10):
		news_data_list[i] = pattern.findall(str(news_data_list[i]))[0]


	table_service = TableService(account_name='newsaggregate', account_key='Im/sg8ePug9VWN14eZNbrMN10QCQC6s+spgO9JuYSRwa2wWwe1SrPQZ7nu+nEdt+dz/Mb/sBfIh3SUUU20QloQ==')
	table_service.create_table('newstable')
	task = Entity()
	taglines = []
	for news in news_list:
		tag = news.find('h3')
		try:
			taglines.append(tag.find('a').text)
		except AttributeError:
			pass

	i=1

	for tagline in taglines:
		task = {'PartitionKey': 'newstagline', 'RowKey': str(i), 'taglines' : str(tagline), 'news' : str(news_data_list[i-1])}
		table_service.insert_or_replace_entity('newstable', task)
		i+=1

def main():
	extract_news()
	#Add to database

if __name__ == '__main__':
	main()