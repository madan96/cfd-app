from flask import Flask, request, jsonify
import MySQLdb
import traceback
import json
#from werkzeug.datastructures import ImmutableMultiDict
app = Flask(__name__)
cursor = None
db = None
def connect_database():
	global cursor,db
	# Open database connection
	db = MySQLdb.connect("127.0.0.1", "root", "3TzZKV^p3ED{", "krishi")
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
	query = "SELECT password FROM users WHERE Email= '%s' " % (credentials_json['email'])
	try :
		cursor.execute(query)
		password = cursor.fetchone()[0]
		print password
		db.commit()
		if password == credentials_json['pass'] :	
			return '{"status":"1"}'
		else :
			return '{"status":"0"}'
	except TypeError :
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
	query = "INSERT INTO users(Email,FirstName,LastName,contact_no,password) values ('%s','%s','%s','%s','%s') " % (details_json['email'] ,details_json['first_name'] , details_json['last_name'] , details_json['contact_no'],details_json['password'])
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
		i=0
		return_dict = dict()
		print "User requested pesticide"
		for data in price_data :
			if data['producttype'] == 'pesticide' :
				return_dict[i] = data
				i+=1
		return jsonify(return_dict)
	elif user_request['request'] == 'fertilizer' :
		i=0
		return_dict = dict()
		print "User requested fertilizer"
		for data in price_data :
			if data['producttype'] == 'fertilizer' :
				return_dict[i] = data
				i+=1
		return jsonify(return_dict)
	elif user_request['request'] == 'seed' :
		i=0
		return_dict = dict()
		print "User requested seed"
		for data in price_data :
			if data['producttype'] == 'seeds' :
				return_dict[i] = data
				i+=1
		return jsonify(return_dict)


@app.route('/check/<uuid>', methods=['GET', 'POST'])
def add_message(uuid):
#    print request.status_code
	print request.method
	print request.form.to_dict()
   # imd = request.form

	return jsonify({"uuid":uuid})


@app.route('/uploader', methods = ['GET', 'POST'])
def upload_file():
	print "Got a file"
	if request.method == 'POST':
	   f = request.files['file']
	   try :
		f.save("/home/snorloks/cfd-app/models/testing/img.jpeg")
		return jsonify({"status":1})
	   except :
		return jsonify({"status":0})

	   



if __name__ == '__main__':
	app.run(port=8000,debug=True)