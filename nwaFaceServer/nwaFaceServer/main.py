'''
Created on 2018年2月2日

@author: root
'''

import os, time, redis, pickle, face_recognition

from flask import Flask, request
from werkzeug.utils import secure_filename

from globals import Globals


app = Flask(__name__)
con = redis.StrictRedis(connection_pool=redis.ConnectionPool(decode_responses=True))

globalsCur = Globals(__file__, 1)
imgEncKey = globalsCur.key("imgEnc")


@app.route("/")
def root():
    return ""


# http://192.168.0.78:5000/face/upload
@app.route("/face/upload", methods=["GET", "POST"])
def upload():
    if request.method == "GET":
        return """
        <form method="POST" enctype="multipart/form-data" >
            <input type="file" name="file" /><br /><br />
            <input type="submit" value="submit" />
        </form>
                """
    else:
        f = request.files["file"]
        fpath = os.path.join(globalsCur.project(), "upload", str(int(time.time())) + "_" + secure_filename(f.filename))
        f.save(fpath)
        imgEnc = face_recognition.face_encodings(face_recognition.load_image_file(f))
        if len(imgEnc) > 0:
            imgEnc = imgEnc[0]
            imgEncs = []
            for imgEncInfo in con.hvals(imgEncKey):
                imgEncs.append(pickle.loads(imgEncInfo)[0])
            imgMatchs = face_recognition.compare_faces(imgEncs, imgEnc)
            if True in imgMatchs:
                return pickle.loads(con.hget(imgEncKey, imgEncs[imgMatchs.index(True)]))[1]
            else:
                f.seek(0, os.SEEK_SET)
                f.save(fpath)
                con.hset(imgEncKey, imgEnc, pickle.dumps([imgEnc, fpath]))
                return "upload success!"
        else:
            return "img error!"


if __name__ == '__main__':
    app.run(host="192.168.0.78", debug=True)
