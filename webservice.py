import json

import os
from flask import jsonify
from flask import *
import MySQLdb
from werkzeug.utils import secure_filename

root=Flask(__name__)
root.secret_key='hai'
path="C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\Photos"
path1="C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\post"

con=MySQLdb.connect(host='localhost',user='root',password='root',port=3306,db='project_db')
cmd=con.cursor()

@root.route('/login',methods=['GET','post'])
def login():
    username=request.args.get('uname')
    password=request.args.get('pass')
    print(username)
    cmd.execute("select * from login_tb where User_name='" + username + "' and Password='" + password + "'")
    s=cmd.fetchone()
    print(s)
    if(s is None):
        return jsonify({'task':"invalid"})
    elif(s[3]=='user'):
        id = s[0]
        print("id", id)
        session['id'] = id
        return jsonify({'task': id})
    else:
        return jsonify({'task': "invalid"})

@root.route('/signup',methods=['POST','GET'])
def signup():
    name=request.form['name']
    gender=request.form['gender']
    dob=request.form['dob']
    city=request.form['city']
    email=request.form['email']
    mobile=request.form['mobile']
    password=request.form['pass']
    photo=request.files['files']
    fname=secure_filename(photo.filename)
    photo.save(os.path.join(path,fname))
    cmd.execute("insert into reg_tb values(null,'"+name+"','"+gender+"','"+dob+"','"+city+"','"+email+"','"+mobile+"','"+password+"','"+fname+"')")
    con.commit()
    return jsonify({'task':"success"})

@root.route('/feedback',methods=['POST','GET'])
def feedback():
    feed=request.args.get('feed')
    lid=request.args.get('lid')
    print(lid)
    print("insert into feedback_tb values(null,'"+lid+"','"+feed+"',curdate())")
    cmd.execute("insert into feedback_tb values(null,'"+lid+"','"+feed+"',curdate())")
    con.commit()
    return jsonify({'task':"success"})

@root.route('/notification',methods=['POST','GET'])
def notification():
    cmd.execute("select * from notification_tb")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)



@root.route('/profile',methods=['POST','GET'])
def profile():
    lid=request.args.get('lid')
    cmd.execute("select * from reg_tb where Login_id='"+lid+"'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@root.route('/post_photos',methods=['POST','GET'])
def post_photos():
    photo = request.files['files']
    fname1 = secure_filename(photo.filename)
    photo.save(os.path1.join(path1, fname1))
    status=request.form['status']
    cmd.execute("insert into post_tb values(null,'"+fname1+"','"+status+"') ")
    con.commit()
    return jsonify({'task':"success"})

@root.route('/view_friends',methods=['POST','GET'])
def view_friends():
    cmd.execute("select * from friends_tb")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@root.route('/view_gallery',methods=['POST','GET'])
def view_gallery():
    cmd.execute("select * from gallery")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@root.route('/view_frequest',methods=['POST','GET'])
def view_frequest():
    cmd.execute("select * from friends_tb")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@root.route('/timeline',methods=['POST','GET'])
def timeline():
    cmd.execute("select * from post_tb")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@root.route('/accept_request',methods=['POST','GET'])
def accept_request():
    f_id=request.args.get('f_id')
    cmd.execute("update friends_tb set status='Accepted' where Friend_id='"+f_id+"' ")
    con.commit()
    return jsonify({'task':"success"})
@root.route('/reject_request',methods=['POST','GET'])
def reject_request():
    f_id=request.args.get('f_id')
    cmd.execute("update friends_tb set status='Rejected' where Friend_id='" + f_id + "' ")
    con.commit()
    return jsonify({'task': "success"})

@root.route('/send_request')
def send_request():
    id=request.args.get('lid')
    cmd.execute("SELECT * FROM `reg_tb` WHERE `U_id` NOT IN (SELECT `User_id` FROM `friends_tb` WHERE `Friend_id`='"+str(id)+"') OR `U_id` NOT IN (SELECT `Friend_id` FROM `friends_tb` WHERE `User_id`='"+str(id)+"')")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@root.route('/f_request',methods=['POST','GET'])
def f_request():
    lid= request.args.get('User_id')
    fid= request.args.get('Friend_id')
    status = request.args.get('Status')
    cmd.execute("insert into friends_tb values(null,'"+lid+"','"+fid+"','"+status+"')")
    con.commit()
    return jsonify({'task': "success"})



@root.route('/forgot_pw',methods=['POST','GET'])
def forgot_pw():
    uname=request.args.get['uname']
    email=request.args.get['email']

    return jsonify({'task': "success"})



if __name__ == "__main__":
    root.run(host="192.168.43.235", port=5000)












