package com.chengxiaoxiao.jsonparse;

public class Person
{
    private String errNum;
    private  String retMsg;
    private RetData retData;

    public String getErrNum()
    {
        return errNum;
    }

    public void setErrNum(String errNum)
    {
        this.errNum = errNum;
    }

    public String getRetMsg()
    {
        return retMsg;
    }

    public void setRetMsg(String retMsg)
    {
        this.retMsg = retMsg;
    }

    public RetData getRetData()
    {
        return retData;
    }

    public void setRetData(RetData retData)
    {
        this.retData = retData;
    }
}
