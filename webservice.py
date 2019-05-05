import json
import smtplib
from email.mime.text import MIMEText
import os

import cv2
from flask import jsonify
from flask.globals import request, session
from flask import Flask,jsonify
from flask_mail import Mail,Message
from flask import *
import MySQLdb
import cognitive_face as CF
from werkzeug.utils import secure_filename

from privacy import facerecognition

root=Flask(__name__)
root.secret_key='hai'
mail=Mail(root)
root.config['MAIL_SERVER']='smtp.gmail.com'

root.config['MAIL_PORT'] = 587
root.config['MAIL_USERNAME'] = 'anjalibnair24@gmail.com'
root.config['MAIL_PASSWORD'] = 'pootholi96'
root.config['MAIL_USE_TLS'] = False
root.config['MAIL_USE_SSL'] = True

path="C:\\Users\\lenovo\PycharmProjects\\My Privacy\\privacy\\static\\"
# path1="C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\post"

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
            passwrd=request.form['password']
            files1=request.files['files']
            UPLOAD_FOLDER=path
            img= secure_filename(files1.filename)

            cmd.execute("insert into login_tb values(NULL,'" + str(email) + "','" + str(passwrd) + "','user')")
            id = con.insert_id()

            img2 = str(id) + ".jpg"
            os.makedirs('C:\\Users\\lenovo\PycharmProjects\\My Privacy\\privacy\\static\\image\\'+str(id))
            # os.makedirs('C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\imgs\\' + str(id))
            os.makedirs('C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\profile\\'+str(id))
            # files1.save(os.path.join(path+"imgs",img2))
            # files1.save(os.path.join(UPLOAD_FOLDER +"imgs\\"+str(id), img2))
            files1.save(os.path.join(UPLOAD_FOLDER + "image\\" + str(id), img2))
            print(str(id))
            img = str(id) + ".jpg"
            files1.save(os.path.join(UPLOAD_FOLDER+ "profile\\"+str(id),img2))
            # print("imggssssssssssssss",img)
            fr = facerecognition.add_face()
            faceid = fr.add_face("static/image/"+str(id)+"/"+img)
            print("f___",faceid)
            cmd.execute("insert into reg_tb values('"+str(id)+"','"+str(fname)+""+str(lname)+"','"+str(city)+"','"+str(gender)+"','"+str(dob)+"','"+str(email)+"','"+str(mobile)+"','"+str(img)+"','"+str(faceid)+"' )")
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
        cmd.execute("select distinct post_tb.Post_id,reg_tb.Name,post_tb.Description,post_tb.Date from friends_tb,post_tb,reg_tb where (friends_tb.User_id='"+str(lid)+"' or  friends_tb.Friend_id='"+str(lid)+"') and friends_tb.Status='Accepted'and ( friends_tb.Friend_id= post_tb.U_id or friends_tb.User_id= post_tb.U_id) and reg_tb.U_id=post_tb.U_id")
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
            # result1.append(result[4])
            json_data.append(dict(zip(row_headers,result1)))
            print("timeline==========",json_data)
        con.commit()
        return jsonify(json_data)
    except Exception as e:
        print(e)
        return jsonify({'task':'None'})

#------Feedback ------
@root.route('/feedback',methods=['POST','GET'])
def feedback():
    feed=request.args.get('feed')
    lid=request.args.get('lid')
    print(lid)
    print("insert into feedback_tb values(null,'"+lid+"','"+feed+"',curdate())")
    cmd.execute("insert into feedback_tb values(null,'"+lid+"','"+feed+"',curdate())")
    con.commit()
    return jsonify({'task':"success"})


#------ Notifications ------
@root.route('/notification',methods=['GET','POST'])
def notification():
    lid=request.args.get('lid')
    print(lid)
    cmd.execute("select poststatus.id,poststatus.postid,post_tb.Description,post_tb.Image,post_tb.Date,reg_tb.Name from poststatus,post_tb,reg_tb where poststatus.postid=post_tb.Post_id and post_tb.U_id=reg_tb.U_id and poststatus.fid='"+str(lid)+"' and poststatus.status='pending'")
    row_headers = [x[0] for x in cmd.description]  # this will extract row headers
    results = cmd.fetchall()
    json_data = []
    for result in results:
        result1=[]
        result1.append(result[0])
        result1.append(result[1])
        result1.append(result[2])
        result1.append(result[3])
        result1.append(result[4])
        # result1.append(str(result[5]))
        result1.append(str(result[5]) + " Posted a photo of you")
        json_data.append(dict(zip(row_headers, result1)))
    con.commit()
    # print("rr---",json_data)
    return jsonify(json_data)

#------ Accept notification ------
@root.route('/acceptnotification',methods=['POST','GET'])
def acceptnotification():
    try:
        postid = request.args.get('postid')
        print(postid)
        lid=request.args.get('lid')

        cmd.execute("update poststatus set status='accepted' where postid='"+postid+"' and fid='"+lid+"'")
        con.commit()
        print(postid)
        cmd.execute("select fid from poststatus where status='pending' AND postid='"+postid+"'")
        f=cmd.fetchall()
        print(f)
        ff='0'
        for f1 in f:
            ff = ff + "#" + str(f1[0])
        cmd.execute("select Image,Post_id from post_tb where Post_id='"+postid+"'")
        s=cmd.fetchone()
        print("img=",str(s[0]))
        print("post id",s[1])
        session['ff'] = ff
        print("static/images/" + str(s[0]) + "@@@" + str(ff) +"@@@" +str(postid) + "@@@" + str(session['ff'])+"@@@00")
        fid = rec_face1("static/images/" + str(s[0]) + "@@@" + str(ff) +"@@@" +str(postid) + "@@@" + str(session['ff'])+"@@@00")
        print("fid",fid)
        return jsonify({'tasks': "success"})
    except Exception as e:
        print("error",e)
        return jsonify({'tasks': "failed"})



# ------ Reject notification ------
@root.route('/rejectnotification', methods=['POST', 'GET'])
def rejectnotification():
    try:
        postid = request.args.get('postid')
        print(postid)
        lid = request.args.get('lid')

        cmd.execute("update poststatus set status='rejected' where postid='" + postid + "' and fid='" + lid + "'")
        con.commit()

        return jsonify({'tasks': "success"})
    except Exception as e:
        return jsonify({'tasks': "failed"})




# @root.route('/notification',methods=['POST','GET'])
# def notification():
#     cmd.execute("select * from notification_tb")
#     row_headers = [x[0] for x in cmd.description]
#     results = cmd.fetchall()
#     json_data = []
#     for result in results:
#         json_data.append(dict(zip(row_headers, result)))
#     con.commit()
#     print(json_data)
#     return jsonify(json_data)

# ---------- Profile ---------------------

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



# @root.route('/post_photos',methods=['POST','GET'])
# def post_photos():
#     photo = request.files['files']
#     fname1 = secure_filename(photo.filename)
#     photo.save(os.path1.join(path, fname1))
#     status=request.form['status']
#     cmd.execute("insert into post_tb values(null,'"+fname1+"','"+status+"') ")
#     con.commit()
#     return jsonify({'task':"success"})


#-------Post Photoss ----------------

@root.route('/post',methods=['POST','GET'])
def post():
    try:
        desc=request.form['desc']
        status=request.form['status']
        lid=request.form['lid']


        print(lid)
        iim=request.files['files']
        img=secure_filename(iim.filename)
        print(img)
        iim.save(os.path.join("C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\images\\"+img))
        cmd.execute("select Login_id from login_tb where  Login_id !="+str(lid))

        # cmd.execute("select lid from users where status='PRIVATE' AND lid in(select  fid from friends where uid='" + lid + "'and status='friends' union  select uid from friends where fid='" + lid + "' and status='friends')")

        f=cmd.fetchall()
        print(f)
        cmd.execute("insert into post_tb values(NULL,'" + str(lid) + "','"+ str(img)+"','" + str(desc) + "',CURDATE())")
        iddd=con.insert_id()
        print(iddd)
        con.commit()


        ff='0'
        for f1 in f:
            ff=ff+"#"+str(f1[0])
        session['ff']=ff
        print("///////",ff)
        fid=rec_face("static/images/"+img+"@@@"+ff+"@@@"+str(iddd))
        # print('faaaiiiaiaiai')
        #
        # print(session['v'],"ssssssss")
        #
        # print(fid)
        # rec_face("static/images/IMG_3496.JPG@@@0#53")
        # for id in fid:
        #     cmd.execute("insert into poststatus values(NULL,'" + str(lid) + "','" + str(id) + "','pending')")
        # con.commit()
        return jsonify({'tasks': "success"})
    except Exception as e:
        print("errrr",str(e)+"   errrrrrr")
        print("errrr",str(e))
        return jsonify({'tasks': "failed"})


def rec_face(imagepath):
    print(imagepath)

    path = str(imagepath).split('@@@')

    print(path, "----------------")

    imagepath = path[0]
    idddss = path[1].split('#')
    pid=path[2]
    uid=path[2]
    print(idddss)
    ssssss=imagepath
    image = cv2.imread(imagepath)
    try:
        key = '30831a43b05b42fcbf5cfd5fbbdd5ffc'  # Replace with a valid Subscription Key here.
        CF.Key.set(key)
        base_url = 'https://westcentralus.api.cognitive.microsoft.com/face/v1.0/'  # Replace with your regional Base URL
        CF.BaseUrl.set(base_url)
        img_urls = []
        img_ids=[]
        for s in idddss:
            if str(s)!="0":
                img_urls.append("C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\image\\"+str(s)+"\\"+str(s)+".jpg")
                img_ids.append(s)
        fc = imagepath
        fc = CF.face.detect(fc)
        print(fc,"fc===========")
        v = False
        i=0
        for k in img_urls:

            faces = CF.face.detect(k)

            print(faces, fc)
            for fc1 in fc:
                print(fc1)
                similarity = CF.face.verify(fc1['faceId'], faces[0]['faceId'])
                if similarity['isIdentical']:
                    cmd.execute("select * from  poststatus where postid=" + str(path[2]) + " and fid=" + str(img_ids[i]) + "")
                    sss=cmd.fetchone()
                    if sss is None:
                        # cv2.rectangle(image, (fc1['faceRectangle']['left'], fc1['faceRectangle']['top']), (int(fc1['faceRectangle']['left'])+(int(fc1['faceRectangle']['width'])), (int(fc1['faceRectangle']['top']))+int(fc1['faceRectangle']['height'])), (0, 255, 0), cv2.FILLED)
                        #
                        # cv2.rectangle(image, (left, top), (right, bottom), (0, 255, 0), cv2.FILLED)
                        top=fc1['faceRectangle']['top']
                        left=fc1['faceRectangle']['left']
                        right=int(fc1['faceRectangle']['left'])+(int(fc1['faceRectangle']['width']))
                        bottom=(int(fc1['faceRectangle']['top']))+int(fc1['faceRectangle']['height'])
                        crop_img = image[top:bottom, left:right]
                        blurred = cv2.blur(crop_img, (20, 100), 0)

                        height, width, channels = blurred.shape

                        print (width, height)
                        for ii in range(0, height):
                            for j in range(0, width):
                                image[top + ii][left + j] = blurred[i][j]


                        cmd.execute("insert into poststatus values(null,"+str(path[2])+","+str(img_ids[i])+",'pending')")
                        con.commit()
            i=i+1


    except Exception as e:
        print(str(e))
    cv2.cv2.imwrite('C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\faces\\' + pid + ".jpg", image)
    return "okkk"


def rec_face1(imagepath):
    print(imagepath)

    path = str(imagepath).split('@@@')

    print(path, "----------------")

    imagepath = path[0]
    idddss = path[1].split('#')
    pid=path[2]
    uid=path[2]
    print(idddss)
    ssssss=imagepath
    image = cv2.imread(imagepath)
    try:
        key = '30831a43b05b42fcbf5cfd5fbbdd5ffc'  # Replace with a valid Subscription Key here.
        CF.Key.set(key)
        base_url = 'https://westcentralus.api.cognitive.microsoft.com/face/v1.0/'  # Replace with your regional Base URL
        CF.BaseUrl.set(base_url)
        img_urls = []
        img_ids=[]
        for s in idddss:
            if str(s)!="0":
                img_urls.append("C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\image\\"+str(s)+"\\"+str(s)+".jpg")
                img_ids.append(s)
        fc = imagepath
        fc = CF.face.detect(fc)
        print(fc,"fc===========")
        v = False
        i=0
        for k in img_urls:

            faces = CF.face.detect(k)

            print(faces, fc)
            for fc1 in fc:
                print(fc1)
                similarity = CF.face.verify(fc1['faceId'], faces[0]['faceId'])
                if similarity['isIdentical']:
                    cmd.execute("select * from  poststatus where postid=" + str(path[2]) + " and fid=" + str(img_ids[i]) + " and status!='accepted'")
                    sss=cmd.fetchone()
                    if sss is not None:
                        # cv2.rectangle(image, (fc1['faceRectangle']['left'], fc1['faceRectangle']['top']), (int(fc1['faceRectangle']['left'])+(int(fc1['faceRectangle']['width'])), (int(fc1['faceRectangle']['top']))+int(fc1['faceRectangle']['height'])), (0, 255, 0), cv2.FILLED)
                        #
                        # cv2.rectangle(image, (left, top), (right, bottom), (0, 255, 0), cv2.FILLED)
                        top=fc1['faceRectangle']['top']
                        left=fc1['faceRectangle']['left']
                        right=int(fc1['faceRectangle']['left'])+(int(fc1['faceRectangle']['width']))
                        bottom=(int(fc1['faceRectangle']['top']))+int(fc1['faceRectangle']['height'])
                        crop_img = image[top:bottom, left:right]
                        blurred = cv2.blur(crop_img, (20, 100), 0)

                        height, width, channels = blurred.shape

                        print (width, height)
                        for ii in range(0, height):
                            for j in range(0, width):
                                image[top + ii][left + j] = blurred[i][j]


                        # cmd.execute("insert into poststatus values(null,"+str(path[2])+","+str(img_ids[i])+",'pending')")
                        # con.commit()
            i=i+1


    except Exception as e:
        print(str(e))
    cv2.cv2.imwrite('C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\faces\\' + pid + ".jpg", image)
    return "okkk"






#------ Friendss ---------------
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



@root.route('/view_frequest',methods=['POST','GET'])
def view_frequest():
    lid = request.args.get('lid')
    cmd.execute("SELECT `reg_tb`.`U_id`,`reg_tb`.`Name`,`reg_tb`.`photo`,`reg_tb`.`City` FROM `reg_tb` JOIN `friends_tb` ON `reg_tb`.`U_id`=`friends_tb`.`User_id` WHERE `friends_tb`.`Friend_id`='"+str(lid)+"' AND `friends_tb`.`Status`='pending' ")
    # cmd.execute("SELECT * FROM `reg_tb` JOIN `friends_tb` ON `reg_tb`.`U_id`=`friends_tb`.`User_id` WHERE (`friends_tb`.`Friend_id`='"+str(lid)+"' AND `friends_tb`.`Status`='pending')")
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
    cmd.execute("SELECT * FROM `reg_tb` INNER JOIN `login_tb` ON `login_tb`.`Login_id` =`reg_tb`.`U_id` AND `login_tb` .`Type`='user' AND `reg_tb`.`U_id` NOT IN (SELECT `User_id` FROM `friends_tb` WHERE `Friend_id`='"+str(id)+"' UNION SELECT `Friend_id` FROM `friends_tb` WHERE `User_id`='"+str(id)+"') AND `reg_tb`.`U_id`!='"+str(id)+"'")
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

# @root.route('/timeline2',methods=['POST','GET'])
# def timeline2():
#     cmd.execute("select * from post_tb")
#     row_headers = [x[0] for x in cmd.description]
#     results = cmd.fetchall()
#     json_data = []
#     for result in results:
#         json_data.append(dict(zip(row_headers, result)))
#     con.commit()
#     print(json_data)
#     return jsonify(json_data)
#


#------Send Chat Messages ------
@root.route('/insertchat',methods=['POST','GET'])
def insertchat():

    try:
        fid=request.args.get('To_id')
        lid=request.args.get('lid')
        msg=request.args.get('Message')
        s=cmd.execute("insert into chat_tb values(NULL,'"+lid+"','"+fid+"','"+msg+"',CURDATE())")
        con.commit()
        print(s)
        return jsonify({'tasks':"success"})
    except Exception as e:
        return jsonify({'tasks':"failed"})

# ------ View Chat Messages ------
@root.route('/viewchat', methods=['GET', 'POST'])
def viewchat():
        try:
            print('hiii')
            id = request.args.get('To_id')
            lid = request.args.get('lid')
            cmd.execute("select * from chat_tb where (From_id='" + lid + "' and To_id='" + id + "') or (From_id='" + id + "' and To_id='" + lid + "') order by date asc")
            row_headers = [x[0] for x in cmd.description]  # this will extract row headers
            results = cmd.fetchall()
            print(results)
            json_data = []
            for result in results:
                json_data.append(dict(zip(row_headers, result)))
            con.commit()
            print(results, json_data)
            return jsonify(json_data)
        except Exception as e:
            print('error', str(e))


#------ Forgot Password ------

@root.route('/forgotpassword',methods=['GET','POST'])
def forgotpassword():
    email=request.args.get('Email')
    cmd.execute("select * from login_tb where User_name='"+str(email)+"'")
    s=cmd.fetchone()
    print(s)
    #email = request.args.get('email')
    # msg = Message('Booking Confirmed ', sender='project2016mails@gmail.com', recipients=["select email from booking where status='accepted'"])
    # msg.body = 'Confirmed your booking'
    # mail.send(msg)
    try:
        gmail = smtplib.SMTP('smtp.gmail.com', 587)

        gmail.ehlo()

        gmail.starttls()

        gmail.login('anjalibnair24@gmail.com','pootholi96')

    except Exception as e:
        print("Couldn't setup email!!"+str(e))

    msg = MIMEText("Your password is " + s[2]  )

    msg['Subject'] = 'MyPrivacy...MyDecision'

    msg['To'] = email

    msg['From'] = 'anjalibnair24@gmail.com'

    try:

        gmail.send_message(msg)

    except Exception as e:

        print("COULDN'T SEND EMAIL", str(e))

    return jsonify({'tasks':"success"})

#------ Gallery---------------------

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


if __name__ == "__main__":
    root.run(host="192.168.43.235", port=5000)












