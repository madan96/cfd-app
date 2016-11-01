import urllib2
import re
from azure.storage.table import TableService, Entity
from bs4 import BeautifulSoup
import json

def extract_data():
	data = {}
	open('data.txt', 'w').close()
	table_service = TableService(account_name='newsaggregate', account_key='Im/sg8ePug9VWN14eZNbrMN10QCQC6s+spgO9JuYSRwa2wWwe1SrPQZ7nu+nEdt+dz/Mb/sBfIh3SUUU20QloQ==')
	for i in range(1,11):
		task = table_service.get_entity('newstable', 'newstagline', str(i))
		data['headline'] = task.taglines
		data['news'] = task.news
		with open('data.txt', 'a') as outfile:
			json.dump(data, outfile, indent=4, sort_keys=True, separators=(',', ':'))

def main():
	extract_data()

if __name__ == '__main__':
	main()