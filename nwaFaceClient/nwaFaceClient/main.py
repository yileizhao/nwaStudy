'''
Created on 2018年2月10日

@author: Administrator
'''

import os
from tkinter.constants import VERTICAL

from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.button import Button
from kivy.uix.camera import Camera
import requests

from nwaCore.globals import Globals

globalsCur = Globals(__file__, 1)


class MainScreen(BoxLayout):

    def __init__(self, **kwargs):
        super(MainScreen, self).__init__(**kwargs)
        self.orientation = VERTICAL
        self.add_widget(Camera(id="camera", resolution=(640, 480), play=True))
        self.add_widget(Button(text="check", on_press=lambda x: self.capture()))
    
    def capture(self):
        fpath = os.path.join(globalsCur.project(), "tmp.png")
        self.children[1].export_to_png(fpath)
        print(requests.post("http://192.168.0.78:5000/face/upload", files={"file": open(fpath, "rb")}).content)

        
class MyApp(App):
    
    def build(self):
        return MainScreen()
    
    
if __name__ == '__main__':
    MyApp().run()
