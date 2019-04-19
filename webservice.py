import json

import os
from flask import jsonify
from flask import *
import MySQLdb
from werkzeug.utils import secure_filename

from privacy import facerecognition

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
    try:
        email = request.form['email']
        cmd.execute("SELECT `User_name` FROM login_tb WHERE `User_name`='"+email+"'")
        e = cmd.fetchone()
        print(email)
        if e is None:
            fname=request.form['fname']
            lname = request.form['lname']
            gender=request.form['gender']
            city=request.form['city']
            dob=request.form['dob']
            mobile = request.form['mobile']
            passwrd=request.form['passwrd']
            conf=request.form['confirmpass']
            # type = request.form['type']
            files1=request.files['filesk']

            UPLOAD_FOLDER=path
            img= secure_filename(files1.filename)

            s=cmd.execute("insert into login_tb values(NULL,'" + str(email) + "','" + str(passwrd) + "','user')")
            id = con.insert_id()
            img1 = str(id) + ".jpg"
            img2 = str(id) + ".jpg"
            os.makedirs('C:\\Users\\lenovo\PycharmProjects\\My Privacy\\static\\image\\'+str(id))
            os.makedirs('C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\static\\imgs\\' + str(id))
            os.makedirs('C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\static\\profile\\'+str(id))
            #files1.save(os.path.join(UPLOAD_FOLDER +"img\\"+str(id), img1))
            files1.save(os.path.join(UPLOAD_FOLDER + "image\\" + str(id), img2))
            print(str(id))
            imgs=str(id)+".jpg"
            files1.save(os.path.join(UPLOAD_FOLDER+"profile\\"+str(id),imgs))
            print(img)
            fr = facerecognition.add_face()
            faceid = ''#fr.add_face("static/image/"+str(id)+"/"+imgs)
            cmd.execute("insert into reg_tb values('"+str(id)+"','"+str(fname)+"','"+str(lname)+"','"+str(city)+"','"+str(gender)+"','"+str(dob)+"','"+str(email)+"','"+str(mobile)+"',"+str(imgs)+"','"+faceid+"' )")
            con.commit()
            return jsonify({'tasks': "success"})
        else:
            print ('fghjk')
            return jsonify({'tasks':"failed"})
    except Exception as e:
            print("errrr",str(e))
            return jsonify({'tasks': "failed"})
#------ Home Page -------
@root.route('/timeline',methods=['POST','GET'])
def timeline():
    try:
        lid=request.args.get('lid')
        print(lid)
        cmd.execute("select distinct post.pid,users.fname,post.text,post.date,post.likes from friends,post,users where (friends.uid='"+str(lid)+"' or  friends.fid='"+str(lid)+"') and friends.status='friends'and ( friends.fid= post.uid or friends.uid= post.uid) and users.lid=post.uid")
        row_headers=[x[0] for x in cmd.description]
        res=cmd.fetchall()
        print(res)
        json_data=[]
        for result in res:
            result1=[]
            result1.append(result[0])
            result1.append(result[1])
            result1.append(result[2])
            result1.append(result[3])
            result1.append(result[4])
            json_data.append(dict(zip(row_headers,result1)))
            print("timeline==========",json_data)
        con.commit()
        return jsonify(json_data)
    except Exception as e:
        return jsonify({'task':'None'})

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
    cmd.execute("select * from reg_tb where U_id='"+lid+"'")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@root.route('/pro_update',methods=['POST','GET'])
def pro_update():
    lid=request.args.get('lid')
    print(lid)
    name = request.args.get('name')
    gender = request.args.get('gender')
    dob= request.args.get('dob')
    city = request.args.get('city')
    email = request.args.get('email')
    mobile = request.args.get('mobile')
    cmd.execute("update reg_tb set Name='"+name+"',Gender='"+gender+"',DOB='"+dob+"',City='"+city+"',Email='"+email+"',Mobile='"+mobile+"' where U_id='"+str(lid)+"'")
    con.commit()
    return jsonify({'task': "success"})



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
    lid=request.args.get('lid')
    print(lid)
    cmd.execute("SELECT `reg_tb`.`U_id`,`reg_tb`.`Name`,`reg_tb`.`photo` FROM `reg_tb` WHERE U_id IN( SELECT `User_id` FROM `friends_tb` WHERE `Friend_id`='"+lid+"' AND `Status`='accepted' UNION SELECT `Friend_id` FROM `friends_tb` WHERE `User_id`='"+lid+"' AND `Status`='accepted')")
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
    lid = request.args.get('lid')
    cmd.execute("SELECT `reg_tb`.`U_id`,`reg_tb`.`Name`,`reg_tb`.`photo`,`reg_tb`.`City` FROM `reg_tb` JOIN `friends_tb` ON `reg_tb`.`U_id`=`friends_tb`.`Friend_id` WHERE `friends_tb`.`User_id`='"+str(lid)+"'AND`friends_tb`.`Status`='pending' ")
    row_headers = [x[0] for x in cmd.description]
    results = cmd.fetchall()
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)

@root.route('/timeline2',methods=['POST','GET'])
def timeline2():
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
    f_id=request.args.get('lid')
    print(f_id)
    cmd.execute("update friends_tb set status='Accepted' where user_id='"+f_id+"' ")
    con.commit()
    return jsonify({'task':"success"})
@root.route('/reject_request',methods=['POST','GET'])
def reject_request():
    f_id=request.args.get('lid')
    cmd.execute("update friends_tb set status='Rejected' where user_id='" + f_id + "' ")
    con.commit()
    return jsonify({'task': "success"})

@root.route('/send_request')
def send_request():
    id=request.args.get('lid')
    print(id)
    cmd.execute("SELECT * FROM `reg_tb` WHERE `U_id` NOT IN (SELECT `User_id` FROM `friends_tb` WHERE `Friend_id`='"+str(id)+"' union SELECT `Friend_id` FROM `friends_tb` WHERE `User_id`='"+str(id)+"') and `U_id`!="+str(id))
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
    print(fid)
    print("insert into friends_tb values(null,'"+lid+"','"+fid+"','')")
    cmd.execute("insert into friends_tb values(null,'"+lid+"','"+fid+"','pending')")
    con.commit()
    return jsonify({'task': "success"})



@root.route('/forgot_pw',methods=['POST','GET'])
def forgot_pw():
    uname=request.args.get['uname']
    email=request.args.get['email']

    return jsonify({'task': "success"})



if __name__ == "__main__":
    root.run(host="192.168.137.1", port=5000)












