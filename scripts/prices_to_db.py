import urllib2
import re
from azure.storage.table import TableService, Entity
from bs4 import BeautifulSoup

base_url = 'http://kisaankranti.com/'
params = ['fertilizer', 'pesticide', 'seeds']

def extract_products():
	table_service = TableService(account_name='pricekeeper', account_key='++V/62CPdKa1MaTiwYVzKWCUpXUfkWZSBJgO4SI6g/NIZ1GkBRixXWvguYxVvb/dpFMkxoI9SYDxwTTS/jTmqw==')
	table_service.create_table('pricetable')
	task = Entity()
	k=1
	for param in params:	
		url = base_url + param + '.html'
		bd = urllib2.Request(url, headers={'User-Agent' : "Magic Browser"}) 
		req = urllib2.urlopen(bd)
		data = BeautifulSoup(req.read(),"lxml")
		details_area = data.find_all("div",{"class":"details-area"})
		products = [] 
		prices = []
		links = []
		for detail in details_area:
			products.append(str(detail.find("h2", {"class":"product-name"}).text))
			atag =  detail.find("h2", {"class":"product-name"}).findAll('a')
			for link in atag:
				links.append(link.get('href'))
			prices.append(detail.find("span", id=lambda x: x and x.startswith('product-')).text)

		for i in range(0,len(prices)):
			prices[i] = re.sub('\s+','',prices[i])
			prices[i] = str(re.sub(r'[^\x00-\x7F]+','', prices[i]))

		m=0
		for product in products:
			task = {'PartitionKey': 'pricetab', 'RowKey': str(k), 'producttype' : param, 'productname' : str(product), 'pricet' : prices[m], 'link' : links[m]}
			table_service.insert_or_replace_entity('pricetable', task)
			m+=1
			k+=1
			

def main():
	extract_products()

if __name__ == '__main__':
	main()