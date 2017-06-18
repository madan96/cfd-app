from flask import Flask, request, jsonify
import MySQLdb
import traceback
import json
import label_image
import urllib
import tagCheck
import os
from werkzeug.datastructures import ImmutableMultiDict
import sys
import json
import pickle
app = Flask(__name__)
cursor = None
db = None
def connect_database():
	global cursor,db
	# Open database connection
	db = MySQLdb.connect("127.0.0.1", "root", os.environ["MYSQL_PASS"], "krishi")
	# prepare a cursor object using cursor() method
	cursor = db.cursor()


@app.route('/')
def hello_world():
	return 'Hello, World!'
@app.route('/hi')
def asd():
	return "HIIII"
@app.route('/login', methods=['GET', 'POST'])
def login():
	if cursor == None :
		connect_database()
	credentials_json = request.form.to_dict()
	print (credentials_json)
	query = "SELECT * FROM users WHERE Email= '%s' " % (credentials_json['email'])
	try :
		cursor.execute(query)
		dbdata = cursor.fetchone()
		print (dbdata)
		password = dbdata[0]
		print password
		print credentials_json['pass']
		db.commit()
		if password == credentials_json['pass'] :	
			print ("Pass cor")
			return jsonify({"status":"1","user_type":dbdata[5]})
		else :
			print ("pass wrong")
			return '{"status":"0"}'
	except TypeError as e :
		print e
		return '{"status":"noemail"}'
	except Exception as e:
		print e
		return "z"
		db.rollback()
	
@app.route('/register', methods=['GET', 'POST'])
def register():
	details_json = request.form.to_dict()
	if cursor == None :
		connect_database()
	query = "INSERT INTO users(Email,FirstName,LastName,contact_no,password,user_type) values ('%s','%s','%s','%s','%s','%s') " % (details_json['email'] ,details_json['first_name'] , details_json['last_name'] , details_json['contact_no'],details_json['password'],details_json['user_type'])
	try :
		cursor.execute(query)
		db.commit()
		return jsonify({"status":"ok"})
	except Exception as e:
		print "In the exception "
		if e[0] == 1062 :
			print "Duplicate entry"
			print jsonify({"status":"dup_user"})
			return jsonify({"status":"dup_user"})
		# print traceback.format_exc()
		return '{"a":"a"}'
		db.rollback()


@app.route('/prices',methods=['GET', 'POST'])
def price():
	user_request=request.form.to_dict()
	price_data = json.load(open('pricedata.txt' , 'r'))
	if user_request['request'] == 'pesticide' :
		print "User reqested pesticide"
		return_list = list()
		for data in price_data :
			if data['producttype'] == 'pesticide' :
				return_list.append(data)
		return jsonify({"data" : return_list})
	elif user_request['request'] == 'fertilizer' :
		return_list = list()
		print "User requested fertilizer"
		for data in price_data :
			if data['producttype'] == 'fertilizer' :
				return_list.append(data)
		return jsonify({"data" : return_list})
	elif user_request['request'] == 'seed' :
		return_list = list()
		print "User requested seed"
		for data in price_data :
			if data['producttype'] == 'seeds' :
				return_list.append(data)
		return jsonify({"data" : return_list})


@app.route('/check/<uuid>', methods=['GET', 'POST'])
def add_message(uuid):
#    print request.status_code
	print request.method
	print request.form.to_dict()
   # imd = request.form

	return jsonify({"uuid":uuid})


@app.route('/news', methods=['GET', 'POST'])
def news() :
	news_data = json.load(open('newsdata.txt' , 'r'))
	return jsonify({"data":news_data})

@app.route('/crop_check', methods = ['GET', 'POST'])
def get_crop(flag=0):
	print "Got a file"
	if flag :
		tag_flag = tagCheck.main("disease_img.jpg")
		if tag_flag :
			result = label_image.main("first_layer_new","disease_img.jpg")
			return result[0]["disease"]
	if request.method == 'POST':
	   userRequest = request.form.to_dict()
	   print userRequest
	   if "imageurl" in userRequest :
	   	fileFromURLFlag = True 
		imageURL = userRequest["imageurl"]
		print "Got file from URL : " + imageURL
	   try :
	   	if not fileFromURLFlag :
			f = request.files['file']
	   except :
		 return jsonify({"status":0})
	   try :
		return_dict = dict()
		if not fileFromURLFlag :
			f.save("/home/snorloks/uploadedImages/crop_img.jpg")
		else :
			urllib.urlretrieve(imageURL, "/home/snorloks/uploadedImages/crop_img.jpg")
			print ("File Saveds")
		tag_flag = tagCheck.main("crop_img.jpg")
		# flag=1
		if tag_flag :
			return_dict["data"] = label_image.main("first_layer_new","crop_img.jpg")[0]
			return_dict["status"] = 5
			return jsonify(return_dict)
		else :
			return jsonify({"status" :2})
	   except :
		print traceback.format_exc()
		return jsonify({"status":0})


@app.route('/disease_check', methods = ['GET', 'POST'])
def get_disease() :
	# print "Finding disease for {}".format(crop_name)
	if request.method == 'POST':
	   userRequest = request.form.to_dict()
	   if "imageurl" in userRequest :
	   	fileFromURLFlag = True 
		imageURL = userRequest["imageurl"]
		print "Got file from URL : " + imageURL
       
	   try :
	   	if not fileFromURLFlag :
			f = request.files['file']
	   except :
		 return jsonify({"status":0})
	   try :
		return_dict = dict()
		if not fileFromURLFlag :
			f.save("/home/snorloks/uploadedImages/disease_img.jpg")
		else :
			urllib.urlretrieve(imageURL, "/home/snorloks/uploadedImages/disease_img.jpg")
		print ("File Saveds")
		flag = tagCheck.main("disease_img.jpg")
		# flag=1
		if flag :
			crop_name = get_crop(1)
			print ("The crop is {}".format(crop_name))
			# label_image.main("banana","disease_img.jpg")
			# label_image.main("first_layer","disease_img.jpg")
			os.system("python /home/snorloks/models/{}/label_image.py {} {}".format(crop_name,"disease_img.jpg",crop_name))
			result = pickle.load(open("/home/snorloks/result.pickle","r"))
			print ("THe result is {}".format(result))
			# print ("The crop is {}".format(crop_name))
			return_dict["data"] = result
			# return_dict["data"] = label_image.main(crop_name,"disease_img.jpg")
			return_dict["status"] = 1
			return jsonify(return_dict)
		else :
			return jsonify({"status" :2})
	   except :
		print traceback.format_exc()
		return jsonify({"status":0})	

@app.route('/buynsell', methods = ['GET', 'POST'])
def buynsell():
	buysell_json = request.form.to_dict()
	print (buysell_json)
	if cursor == None :
		connect_database()
	query = "INSERT INTO cropsale (crop_name,price,qty,farmer_id,description) values ('%s',%d, %d,'%s','%s') " % (buysell_json['crop'] ,int(buysell_json['price']), int(buysell_json['quantity']), buysell_json['email'],buysell_json['description'])
	try :
		cursor.execute(query)
		db.commit()
		return jsonify({"status":"ok"})
	except Exception as e:
		print traceback.format_exc()
		print "In the exception "
	if e[0] == 1062 :
		print "Duplicate entry"
		print jsonify({"status":"dup_user"})
		return jsonify({"status":"dup_user"})
	# print traceback.format_exc()
	return '{"a":"a"}'
	db.rollback()

@app.route('/getproducts', methods = ['GET', 'POST'])
def getproducts():
	userJSON = request.form.to_dict()
	print (userJSON)
	if cursor == None :
		connect_database()
	query = "SELECT * FROM cropsale WHERE farmer_id = '%s' " % userJSON['email']
	try :
		returnData = []
		cursor.execute(query)
		dbdata = cursor.fetchall()
		print (dbdata)
		for data in dbdata :
			returnData.append({"crop_name":data[0] , 
							   "price" : data[1],
							   "quantity" : data[2],
							   "farmer_id" : data[3],
							   "desc" : data[4]})
		return jsonify({"data":returnData})
		'''
		The data will be returned as follows : 
		It will be a list of tuples and individual tuple has following values :
		Index - Value
			0 - Crop name
			1 - price 
			2 - quantity
			3 - farmer_id
			4 - desc
		'''

		# password = dbdata[0]
		# print password
		# print credentials_json['pass']
		# db.commit()
		# if password == credentials_json['pass'] :	
		# 	print ("Pass cor")
		# 	return jsonify({"status":"1","user_type":dbdata[4]})
		# else :
		# 	print ("pass wrong")
		# 	return '{"status":"0"}'
	except TypeError as e :
		print e
		return '{"status":"noprod"}'
	except Exception as e:
		print e
		return "z"
		# db.rollback()

@app.route("/requests" , methods=['GET','POST'])
def farmerRequests() :
	if cursor == None :
		connect_database()
	data = request.form.to_dict()
	farmer_id = data["farmer_id"]
	query = "SELECT * FROM requests WHERE farmer_id = '%s' " % farmer_id
	try :
		returnData = []
		cursor.execute(query)
		dbdata = cursor.fetchall()
		print (dbdata)
		for data in dbdata :
				returnData.append({"id":data[0] , 
							   "buyer_id" : data[2],
							   "quantity" : data[4],
							   "crop_name" : data[5]})
		return jsonify({"data":returnData,"status":"ok"})
	except TypeError as e :
		print e
		return '{"status":"noprod"}'
	except Exception as e :
		print e 
		return '{"status":"error"}'
@app.route("/farmerrequestresponse" , methods=['GET','POST'])
def FarmerRequestResponse():
	'''
	Data Required : 
	"email" : (String) Email id of the farmer 
	"requestflag" : (Boolean) True means farmer accepted the request , False means farmer rejected the request. 
	"requestid" : Unique ID of the request 
	'''
	if cursor == None :
		connect_database()
	formData = request.form.to_dict()
	requestID = formData["requestid"]
	response = formData["response"]
	print (response)
	if "t" in response :
		print (1)
		query = "UPDATE requests SET farmer_response = '%s' WHERE id = '%s' " % ("t",requestID)
		success = "ok"
	elif "no" in response :
		print (2)
		query = "DELETE from requests WHERE id = '%s' " % (requestID)
		success = "delok"
	else :
		query = "None"
	# print (query)
	try :
		cursor.execute(query)
		db.commit()
		return jsonify({"status":success})
	except Exception as e :
		print (e)
		return jsonify({"status":"no"})

@app.route("/listings",methods=['GET',"POST"])
def listings():
	if cursor == None :
		connect_database()
	query = "SELECT * FROM cropsale "
	try :
		returnData = []
		cursor.execute(query)
		dbdata = cursor.fetchall()
		print (dbdata)
		for data in dbdata :
			returnData.append({"crop_name":data[0] , 
							   "price" : data[1],
							   "quantity" : data[2],
							   "farmer_id" : data[3],
							   "desc" : data[4]})
		return jsonify({"data":returnData})
		'''
		The data will be returned as follows : 
		It will be a list of tuples and individual tuple has following values :
		Index - Value
			0 - Crop name
			1 - price 
			2 - quantity
			3 - farmer_id
			4 - desc
		'''

		# password = dbdata[0]
		# print password
		# print credentials_json['pass']
		# db.commit()
		# if password == credentials_json['pass'] :	
		# 	print ("Pass cor")
		# 	return jsonify({"status":"1","user_type":dbdata[4]})
		# else :
		# 	print ("pass wrong")
		# 	return '{"status":"0"}'
	except TypeError as e :
		print e
		return '{"status":"noprod"}'
	except Exception as e:
		print e
		return "z"
		# db.rollback()

@app.route("/buycrop",methods=['GET','POST'])
def buycrop() :
	data = request.form.to_dict()
	farmer_id = data["farmer_id"]
	crop_name = data["crop_name"]
	buyer_id = data["buyer_id"]
	quantity = data["quantity"]
	if cursor == None :
		connect_database()
	query = "INSERT INTO requests (farmer_id,crop_name,buyer_id,quantity) values ('%s','%s', '%s','%s') " % (farmer_id,crop_name,buyer_id,quantity)
	try :
		cursor.execute(query)
		db.commit()
		return jsonify({"status":"ok"})
	except Exception as e:
		print traceback.format_exc()
		print "In the exception "
		return jsonify({"status":"no"})
		db.rollback() 
	# if e[0] == 1062 :
	#     print "Duplicate entry"
	#     print jsonify({"status":"dup_user"})
	#     return jsonify({"status":"dup_user"})
	# # print traceback.format_exc()
	# return '{"a":"a"}'
	   
if __name__ == '__main__':
	try :
		app.run(port=8000,debug=True,threaded=True)
	except Exception as e:
		if e[0] == 48 :
			print "Address already in use"
		else :
			app.run(port=8000,debug=True,threaded=True)
			print "Got the following error:\n{} ".format(traceback.format_exc())
