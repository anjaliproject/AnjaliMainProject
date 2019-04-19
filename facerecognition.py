import requests
# If you are using a Jupyter notebook, uncomment the following line.
#%matplotlib inline
import matplotlib.pyplot as plt
from PIL import Image
# from builtins import print

from flask.globals import session
from matplotlib import patches
from io import BytesIO
class add_face:
    def add_face(self,img):
        try:
            # Replace <Subscription Key> with your valid subscription key.
            subscription_key = "0e5d410651504ace8c66169765c6bea2"
            assert subscription_key

            face_api_url = 'https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect'

            # Set image_url to the URL of an image that you want to analyze.


            headers = {'Ocp-Apim-Subscription-Key': subscription_key}
            params = {
                'returnFaceId': 'true',
                'returnFaceLandmarks': 'false',
                'returnFaceAttributes': 'age,gender'
            }
            # image_path = img

            image_path = img
            print("img--------" + image_path)
            image_data = open(image_path, "rb").read()
            # data = {'url': image_url}
            # print(data)
            # response = requests.post(face_api_url, params=params, headers=headers, json=data)
            # faces = response.json()

            headers = {'Ocp-Apim-Subscription-Key': subscription_key, "Content-Type": "application/octet-stream"}
            response = requests.post(face_api_url, headers=headers,params=params, data=image_data)
            response.raise_for_status()
            faces = response.json()
            print("res=========",response)
            print("face=========", faces)
            # analysis

            # print("faces is---"+str(faces))
            # Display the original image and overlay it with the face information.
            image = Image.open(image_path)
            plt.figure(figsize=(8, 8))
            import numpy as np

            im = np.array(image, dtype=np.uint8)

            ax = plt.imshow(im, alpha=0.6)
            # print(faces)
            print("hiiiiiiiiiiii")
            global fid
            for face in faces:
                fr = face["faceRectangle"]
                fa = face["faceAttributes"]
                fid = face["faceId"]
                print("fid",str(fid))
                # session['fid']=fid
                origin = (fr["left"], fr["top"])
                p = patches.Rectangle(origin, fr["width"], fr["height"], fill=False, linewidth=2, color='b')
                ax.axes.add_patch(p)

                plt.text(origin[0], origin[1], "%s, %d"%(fa["gender"].capitalize(), fa["age"]),
                         fontsize=20, weight="bold", va="bottom")

            # _ = plt.axis("off")
            # plt.show()

            print("fid is----"+str(fid))
            return fid
        except:
            return '1234567890'
# t=add_face()
# t.add_face("static/images/ANIS.jpg")
