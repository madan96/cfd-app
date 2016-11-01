import urllib2
import re
from azure.storage.table import TableService, Entity
from bs4 import BeautifulSoup
import json

def extract_data():
	data = {}
	open('pricedata.txt', 'w').close()
	table_service = TableService(account_name='pricekeeper', account_key='++V/62CPdKa1MaTiwYVzKWCUpXUfkWZSBJgO4SI6g/NIZ1GkBRixXWvguYxVvb/dpFMkxoI9SYDxwTTS/jTmqw==')
	for i in range(1,37):
		task = table_service.get_entity('pricetable', 'pricetab', str(i))
		data['product-type'] = task.producttype
		data['product-name'] = task.productname
		data['price'] = "Rs. " + task.pricet
		with open('pricedata.txt', 'a') as outfile:
			json.dump(data, outfile, indent=4, sort_keys=True, separators=(',', ':'))

def main():
	extract_data()

if __name__ == '__main__':
	main()