package com.koddev.chatapp;

public class writing {
   public String userNmae;

   public String writingType;
   public String writingTitle;
   public String writingContext;


    public writing (String userNmae, String writingTitle, String writingContext, String writingType){
       this.userNmae=userNmae;
       this.writingContext=writingContext;
       this.writingTitle=writingTitle;
       this.writingType=writingType;
    }

    public writing getWriting(){
       return this;
    }
   public String getWritingType() {
      return writingType;
   }

   public void setWritingType(String writingType) {
      this.writingType = writingType;
   }

   public String getUserNmae() {
      return userNmae;
   }

   public void setUserNmae(String userNmae) {
      this.userNmae = userNmae;
   }



   public String getWritingTitle() {
      return writingTitle;
   }

   public void setWritingTitle(String writingTitle) {
      this.writingTitle = writingTitle;
   }

   public String getWritingContext() {
      return writingContext;
   }

   public void setWritingContext(String writingContext) {
      this.writingContext = writingContext;
   }
}




