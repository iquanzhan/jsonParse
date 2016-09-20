package com.chengxiaoxiao.jsonparse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author XiaoXiao
 * @version $Rev$
 */
public class StreamTool
{
    public static String decodeStream(InputStream in)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] bytes = new byte[1024];

            int len = 0;
            while ((len = in.read(bytes)) > 0)
            {
                baos.write(bytes, 0, len);
            }

            String data = baos.toString();
            return data;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }
}
