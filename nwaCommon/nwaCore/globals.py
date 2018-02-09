'''
Created on 2018年2月5日

@author: root
'''

import os

from nwaCore.enums import SignEnum


class Globals(object):
    '''
    classdocs
    '''
    
    def __init__(self, mainFile, projectDepth=0):
        '''
        Constructor
        '''
        self.mainFile = mainFile
        self.projectDepth = projectDepth
        
    def path(self):
        return os.path.split(os.path.realpath(self.mainFile))[0]
    
    def project(self):
        projectPathTmp = self.path()
        count = 0;
        while count < self.projectDepth:
            count += 1
            projectPathTmp = os.path.realpath(os.path.join(projectPathTmp, ".."))
        return projectPathTmp
    
    def projectName(self):
        return self.project().split(os.sep)[-1]
        
    def key(self, key):
        return self.projectName() + SignEnum.colon.value + key
    
