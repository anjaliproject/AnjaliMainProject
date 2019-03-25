from flask import *
import MySQLdb
con=MySQLdb.connect(host='localhost',user='root',password='root',port=3306,db='project_db')
cmd=con.cursor()
root=Flask(__name__)
root.secret_key="project_db"
path="C:\\Users\\lenovo\\PycharmProjects\\My Privacy\\privacy\\static\\Photos"
@root.route('/')
def login():
    return render_template('login.html')
@root.route('/log',methods=['get','post'])
def log():
    name=request.form['textfield']
    password=request.form['textfield2']
    cmd.execute("select * from login_tb where User_name='"+name+"' and Password='"+password+"'")
    s=cmd.fetchone()
    if s is None:
        return '''<script>alert('invalid');window.location='/'</script>'''
    else:
        return render_template('admin_home.html')

@root.route('/admin_home')
def admin_home():
    return render_template('admin_home.html')
@root.route('/View_users')
def View_users():
    cmd.execute("SELECT `reg_tb`.* FROM `reg_tb` JOIN `login_tb` ON `login_tb`.`Login_id`=`reg_tb`.`U_id` WHERE `login_tb`.`Type`='user'")
    x = cmd.fetchall()
    return render_template('View_users.html',val=x)

@root.route('/View_Blockedusers')
def View_Blockedusers():
    cmd.execute("SELECT `reg_tb`.* FROM `reg_tb` JOIN `login_tb` ON `login_tb`.`Login_id`=`reg_tb`.`U_id` WHERE `login_tb`.`Type`='blocked'")
    p=cmd.fetchall()
    return render_template('View_Blockedusers.html',val=p)
@root.route('/unblock')
def unblock():
    Id=request.args.get('Id')
    cmd.execute("update login_tb set Type='user' where Login_id='"+str(Id)+"'")
    con.commit()
    return '''<script>alert("unblocked");window.location="/View_Blockedusers"</script>'''


@root.route('/View_feedback')
def View_feedback():
    cmd.execute("select * from feedback_tb")
    s = cmd.fetchall()
    return render_template('View_feedback.html',val=s)
@root.route('/delete')
def delete():
    id=request.args.get('Id')
    cmd.execute("delete from feedback_tb where Feed_id='"+str(id)+"'")
    con.commit()
    return '''<script>alert("deleted");window.location="/View_feedback"</script>'''
@root.route('/block',methods=['POST','GET'])
def block():
    Id=request.args.get('Id')
    print(Id)
    cmd.execute("update login_tb set Type='blocked' where Login_id='"+str(Id)+"'")
    con.commit()
    return '''<script>alert("Blocked");window.location='/View_users'</script>'''




@root.route('/logout')
def logout():
    return render_template('login.html')





if(__name__=='__main__'):
    root.run(debug=True)


