package com.xy.sweep.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * @author climb.xu
 * @date 2022/3/26 15:01
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
@Component
public class OSSUtil implements InitializingBean {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private static String ENDPOINT;
    private static String BUCKETNAME;
    private static OSSClient ossClient;

    @Override
    public void afterPropertiesSet() {
        BUCKETNAME = bucketName;
        ENDPOINT = endpoint;
        ossClient = (OSSClient) new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

    public static Map<String, Object> upload2Oss(MultipartFile file) {
        //1、限制最大文件为100M
        if (file.getSize() > 1024 * 1024 * 100) {
            return null;
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase(); //文件后缀
        String uuid = UUID.randomUUID().toString();
        Calendar calendar = Calendar.getInstance();
        String dir = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        String filePath = dir + "/" + uuid + suffix;
        try {
            InputStream inputStream = file.getInputStream();
            upload(inputStream, filePath);
            Map<String, Object> map = new HashMap<>();
            map.put("size", getPrintSize(file.getSize()));
            map.put("filePath", "https://" + BUCKETNAME + "." + ENDPOINT + "/" + filePath);
            map.put("fileName", fileName);
            return map;
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败");
        }
    }

    private static String upload(InputStream is, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(is.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            String contentType = getContentType(fileName.substring(fileName.lastIndexOf(".")));
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            PutObjectResult putResult = ossClient.putObject(BUCKETNAME, fileName, is, objectMetadata);
            ret = putResult.getETag();
            System.out.println(ret);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    private static String getContentType(String FilenameExtension) {
        switch (FilenameExtension) {
            case ".":
                return "application/x-";
            case ".*":
                return "application/octet-stream";
            case ".pdf":
                return "application/pdf";
            case ".ai":
                return "application/postscript";
            case ".edi":
                return "application/EDIFACT";
            case ".json":
                return "application/json";
            case ".js":
                return "application/javascript";
            case ".ogg":
                return "application/ogg";
            case ".rdf":
                return "application/rdf+xml";
            case ".woff":
                return "application/font-woff";
            case ".xhtml":
                return "application/xhtml+xml";
            case ".xml":
                return "application/xml";
            case ".dtd":
                return "application/xml-dtd";
            case ".zip":
                return "application/zip";
            case ".gzip":
                return "application/gzip";
            case "0.001":
                return "application/x-001";
            case "0.301":
                return "application/x-301";
            case "0.906":
                return "application/x-906";
            case ".a11":
                return "application/x-a11";
            case ".awf":
                return "application/vnd.adobe.workflow";
            case ".bmp":
                return "application/x-bmp";
            case ".c4t":
                return "application/x-c4t";
            case ".cal":
                return "application/x-cals";
            case ".cdf":
                return "application/x-netcdf";
            case ".cel":
                return "application/x-cel";
            case ".cg4":
                return "application/x-g4";
            case ".cit":
                return "application/x-cit";
            case ".bot":
                return "application/x-bot";
            case ".c90":
                return "application/x-c90";
            case ".cat":
                return "application/vnd.ms-pki.seccat";
            case ".cdr":
                return "application/x-cdr";
            case ".cer":
                return "application/x-x509-ca-cert";
            case ".cgm":
                return "application/x-cgm";
            case ".cmx":
                return "application/x-cmx";
            case ".crl":
                return "application/pkix-crl";
            case ".csi":
                return "application/x-csi";
            case ".cut":
                return "application/x-cut";
            case ".dbm":
                return "application/x-dbm";
            case ".cmp":
                return "application/x-cmp";
            case ".cot":
                return "application/x-cot";
            case ".crt":
                return "application/x-x509-ca-cert";
            case ".dbf":
                return "application/x-dbf";
            case ".dbx":
                return "application/x-dbx";
            case ".dcx":
                return "application/x-dcx";
            case ".dgn":
                return "application/x-dgn";
            case ".dll":
                return "application/x-msdownload";
            case ".dot":
                return "application/msword";
            case ".der":
                return "application/x-x509-ca-cert";
            case ".dib":
                return "application/x-dib";
            case ".doc":
                return "application/msword";
            case ".drw":
                return "application/x-drw";
            case ".dwf":
                return "application/x-dwf";
            case ".dxb":
                return "application/x-dxb";
            case ".edn":
                return "application/vnd.adobe.edn";
            case ".dwg":
                return "application/x-dwg";
            case ".dxf":
                return "application/x-dxf";
            case ".emf":
                return "application/x-emf";
            case ".epi":
                return "application/x-epi";
            case ".eps":
                return "application/postscript";
            case ".exe":
                return "application/x-msdownload";
            case ".fdf":
                return "application/vnd.fdf";
            case ".etd":
                return "application/x-ebx";
            case ".fif":
                return "application/fractals";
            case ".frm":
                return "application/x-frm";
            case ".gbr":
                return "application/x-gbr";
            case ".g4":
                return "application/x-g4";
            case ".gl2":
                return "application/x-gl2";
            case ".hgl":
                return "application/x-hgl";
            case ".hpg":
                return "application/x-hpgl";
            case ".hqx":
                return "application/mac-binhex40";
            case ".hta":
                return "application/hta";
            case ".gp4":
                return "application/x-gp4";
            case ".hmr":
                return "application/x-hmr";
            case ".hpl":
                return "application/x-hpl";
            case ".hrf":
                return "application/x-hrf";
            case ".icb":
                return "application/x-icb";
            case ".ico":
                return "application/x-ico";
            case ".ig4":
                return "application/x-g4";
            case ".iii":
                return "application/x-iphone";
            case ".ins":
                return "application/x-internet-signup";
            case ".iff":
                return "application/x-iff";
            case ".igs":
                return "application/x-igs";
            //case ".img":  return "application/x-img" ;//下载
            case ".img":
                return "image/jpg";//预览
            case ".isp":
                return "application/x-internet-signup";
            case ".jpe":
                return "application/x-jpe";
            //case ".jpg":  return "application/x-jpg" ;下载
            case ".jpg":
                return "image/jpg";//预览
            case ".lar":
                return "application/x-laplayer-reg";
            case ".latex":
                return "application/x-latex";
            case ".lbm":
                return "application/x-lbm";
            case ".ls":
                return "application/x-javascript";
            case ".ltr":
                return "application/x-ltr";
            case ".man":
                return "application/x-troff-man";
            case ".mdb":
                return "application/msaccess";
            case ".mac":
                return "application/x-mac";
            case ".mp3":
                return "audio/mp3";
            // case ".mp4":  return "video/mpeg4";下载
            case ".mp4":
                return "video/mp4";//预览
            case ".mfp":
                return "application/x-shockwave-flash";
            case ".mi":
                return "application/x-mi";
            case ".mil":
                return "application/x-mil";
            case ".mocha":
                return "application/x-javascript";
            case ".mpd":
                return "application/vnd.ms-project";
            case ".mpp":
                return "application/vnd.ms-project";
            case ".mpt":
                return "application/vnd.ms-project";
            case ".mpw":
                return "application/vnd.ms-project";
            case ".mpx":
                return "application/vnd.ms-project";
            case ".mxp":
                return "application/x-mmxp";
            case ".nrf":
                return "application/x-nrf";
            case ".out":
                return "application/x-out";
            case ".p12":
                return "application/x-pkcs12";
            case ".p7c":
                return "application/pkcs7-mime";
            case ".p7r":
                return "application/x-pkcs7-certreqresp";
            case ".pc5":
                return "application/x-pc5";
            case ".pcl":
                return "application/x-pcl";
            case ".pdx":
                return "application/vnd.adobe.pdx";
            case ".pgl":
                return "application/x-pgl";
            case ".pko":
                return "application/vnd.ms-pki.pko";
            case ".p10":
                return "application/pkcs10";
            case ".p7b":
                return "application/x-pkcs7-certificates";
            case ".p7m":
                return "application/pkcs7-mime";
            case ".p7s":
                return "application/pkcs7-signature";
            case ".pci":
                return "application/x-pci";
            case ".pcx":
                return "application/x-pcx";
            case ".pfx":
                return "application/x-pkcs12";
            case ".pic":
                return "application/x-pic";
            case ".pl":
                return "application/x-perl";
            case ".plt":
                return "application/x-plt";
            //case ".png":  return "application/x-png" ;//下载
            case ".png":
                return "image/jpg";//预览
            case ".ppa":
                return "application/vnd.ms-powerpoint";
            case ".pps":
                return "application/vnd.ms-powerpoint";
            case ".ppt":
                return "application/x-ppt";
            case ".prf":
                return "application/pics-rules";
            case ".prt":
                return "application/x-prt";
            case ".ps":
                return "application/postscript";
            case ".pwz":
                return "application/vnd.ms-powerpoint";
            case ".ra":
                return "audio/vnd.rn-realaudio";
            case ".ras":
                return "application/x-ras";
            case ".pot":
                return "application/vnd.ms-powerpoint";
            case ".ppm":
                return "application/x-ppm";
            case ".pr":
                return "application/x-pr";
            case ".prn":
                return "application/x-prn";
            case ".ptn":
                return "application/x-ptn";
            case ".red":
                return "application/x-red";
            case ".rjs":
                return "application/vnd.rn-realsystem-rjs";
            case ".rlc":
                return "application/x-rlc";
            case ".rm":
                return "application/vnd.rn-realmedia";
            case ".rat":
                return "application/rat-file";
            case ".rec":
                return "application/vnd.rn-recording";
            case ".rgb":
                return "application/x-rgb";
            case ".rjt":
                return "application/vnd.rn-realsystem-rjt";
            case ".rle":
                return "application/x-rle";
            case ".rmf":
                return "application/vnd.adobe.rmf";
            case ".rmj":
                return "application/vnd.rn-realsystem-rmj";
            case ".rmp":
                return "application/vnd.rn-rn_music_package";
            case ".rmvb":
                return "application/vnd.rn-realmedia-vbr";
            case ".rnx":
                return "application/vnd.rn-realplayer";
            case ".rpm":
                return "audio/x-pn-realaudio-plugin";
            case ".rms":
                return "application/vnd.rn-realmedia-secure";
            case ".rmx":
                return "application/vnd.rn-realsystem-rmx";
            case ".rsml":
                return "application/vnd.rn-rsml";
            case ".rtf":
                return "application/msword";
            case ".rv":
                return "video/vnd.rn-realvideo";
            case ".sat":
                return "application/x-sat";
            case ".sdw":
                return "application/x-sdw";
            case ".slb":
                return "application/x-slb";
            case ".sam":
                return "application/x-sam";
            case ".sdp":
                return "application/sdp";
            case ".sit":
                return "application/x-stuffit";
            case ".sld":
                return "application/x-sld";
            case ".smi":
                return "application/smil";
            case ".smk":
                return "application/x-smk";
            case ".smil":
                return "application/smil";
            case ".spc":
                return "application/x-pkcs7-certificates";
            case ".spl":
                return "application/futuresplash";
            case ".ssm":
                return "application/streamingmedia";
            case ".stl":
                return "application/vnd.ms-pki.stl";
            case ".sst":
                return "application/vnd.ms-pki.certstore";
            case ".tdf":
                return "application/x-tdf";
            case ".tga":
                return "application/x-tga";
            case ".sty":
                return "application/x-sty";
            case ".swf":
                return "application/x-shockwave-flash";
            case ".tg4":
                return "application/x-tg4";
            case ".tif":
                return "application/x-tif";
            case ".vdx":
                return "application/vnd.visio";
            case ".vpg":
                return "application/x-vpeg005";
            case ".vst":
                return "application/vnd.visio";
            case ".vsw":
                return "application/vnd.visio";
            case ".vtx":
                return "application/vnd.visio";
            case ".torrent":
                return "application/x-bittorrent";
            case ".vda":
                return "application/x-vda";
            case ".vsd":
                return "application/vnd.visio";
            case ".vss":
                return "application/vnd.visio";
            case ".vsx":
                return "application/vnd.visio";
            case ".wb1":
                return "application/x-wb1";
            case ".wb3":
                return "application/x-wb3";
            case ".wiz":
                return "application/msword";
            case ".wk4":
                return "application/x-wk4";
            case ".wks":
                return "application/x-wks";
            case ".wb2":
                return "application/x-wb2";
            case ".wk3":
                return "application/x-wk3";
            case ".wkq":
                return "application/x-wkq";
            case ".wmf":
                return "application/x-wmf";
            case ".wmd":
                return "application/x-ms-wmd";
            case ".wp6":
                return "application/x-wp6";
            case ".wpg":
                return "application/x-wpg";
            case ".wq1":
                return "application/x-wq1";
            case ".wri":
                return "application/x-wri";
            case ".ws":
                return "application/x-ws";
            case ".wmz":
                return "application/x-ms-wmz";
            case ".wpd":
                return "application/x-wpd";
            case ".wpl":
                return "application/vnd.ms-wpl";
            case ".wr1":
                return "application/x-wr1";
            case ".wrk":
                return "application/x-wrk";
            case ".ws2":
                return "application/x-ws";
            case ".xdp":
                return "application/vnd.adobe.xdp";
            case ".xfd":
                return "application/vnd.adobe.xfd";
            case ".xfdf":
                return "application/vnd.adobe.xfdf";
            case ".xls":
                return "application/vnd.ms-excel";
            case ".xwd":
                return "application/x-xwd";
            case ".sis":
                return "application/vnd.symbian.install";
            case ".x_t":
                return "application/x-x_t";
            case ".apk":
                return "application/vnd.android.package-archive";
            case ".x_b":
                return "application/x-x_b";
            case ".sisx":
                return "application/vnd.symbian.install";
            case ".ipa":
                return "application/vnd.iphone";
            case ".xap":
                return "application/x-silverlight-app";
            case ".xlw":
                return "application/x-xlw";
            case ".xpl":
                return "audio/scpls";
            case ".anv":
                return "application/x-anv";
            case ".uin":
                return "application/x-icq";
            case ".001":
                return " application/x-001";
            case ".301":
                return " application/x-301";
            case ".323":
                return " text/h323";
            case ".906":
                return " application/x-906";
            case ".907":
                return " drawing/907";
            case ".acp":
                return " audio/x-mei-aac";
            case ".aif":
                return " audio/aiff";
            case ".aifc":
                return " audio/aiff";
            case ".aiff":
                return " audio/aiff";
            case ".asa":
                return " text/asa";
            case ".asf":
                return " video/x-ms-asf";
            case ".asp":
                return " text/asp";
            case ".asx":
                return " video/x-ms-asf";
            case ".au":
                return " audio/basic";
            case ".avi":
                return " video/avi";
            case ".biz":
                return " text/xml";
            case ".class":
                return " java/*";
            case ".cml":
                return " text/xml";
            case ".css":
                return " text/css";
            case ".dcd":
                return " text/xml";
            case ".eml":
                return " message/rfc822";
            case ".ent":
                return " text/xml";
            case ".fax":
                return " image/fax";
            case ".fo":
                return " text/xml";
            case ".gcd":
                return " application/x-gcd";
            case ".gif":
                return " image/gif";
            case ".htc":
                return " text/x-component";
            case ".htm":
                return " text/html";
            case ".html":
                return " text/html";
            case ".htt":
                return " text/webviewhtml";
            case ".htx":
                return " text/html";
            case ".IVF":
                return " video/x-ivf";
            case ".java":
                return " java/*";
            case ".jfif":
                return " image/jpeg";
            case ".jpeg":
                return " image/jpeg";
            case ".jsp":
                return " text/html";
            case ".la1":
                return " audio/x-liquid-file";
            case ".lavs":
                return " audio/x-liquid-secure";
            case ".lmsff":
                return " audio/x-la-lms";
            case ".m1v":
                return " video/x-mpeg";
            case ".m2v":
                return " video/x-mpeg";
            case ".m3u":
                return " audio/mpegurl";
            case ".m4e":
                return " video/mpeg4";
            case ".math":
                return " text/xml";
            case ".mht":
                return " message/rfc822";
            case ".mhtml":
                return " message/rfc822";
            case ".mid":
                return " audio/mid";
            case ".midi":
                return " audio/mid";
            case ".mml":
                return " text/xml";
            case ".mnd":
                return " audio/x-musicnet-download";
            case ".mns":
                return " audio/x-musicnet-stream";
            case ".movie":
                return " video/x-sgi-movie";
            case ".mov":
                return "video/mp4";//预览
            case ".mp1":
                return " audio/mp1";
            case ".mp2":
                return " audio/mp2";
            case ".mp2v":
                return " video/mpeg";
            case ".mpa":
                return " video/x-mpg";
            case ".mpe":
                return " video/x-mpeg";
            case ".mpeg":
                return " video/mpg";
            case ".mpg":
                return " video/mpg";
            case ".mpga":
                return " audio/rn-mpeg";
            case ".mps":
                return " video/x-mpeg";
            case ".mpv":
                return " video/mpg";
            case ".mpv2":
                return " video/mpeg";
            case ".mtx":
                return " text/xml";
            case ".net":
                return " image/pnetvue";
            case ".nws":
                return " message/rfc822";
            case ".odc":
                return " text/x-ms-odc";
            case ".plg":
                return " text/html";
            case ".pls":
                return " audio/scpls";
            case ".r3t":
                return " text/vnd.rn-realtext3d";
            case ".ram":
                return " audio/x-pn-realaudio";
            case ".rmi":
                return " audio/mid";
            case ".rmm":
                return " audio/x-pn-realaudio";
            case ".rp":
                return " image/vnd.rn-realpix";
            case ".rt":
                return " text/vnd.rn-realtext";
            case ".slk":
                return " drawing/x-slk";
            case ".snd":
                return " audio/basic";
            case ".sol":
                return " text/plain";
            case ".sor":
                return " text/plain";
            case ".spp":
                return " text/xml";
            case ".stm":
                return " text/html";
            case ".svg":
                return " text/xml";
            case ".tiff":
                return " image/tiff";
            case ".tld":
                return " text/xml";
            case ".top":
                return " drawing/x-top";
            case ".tsd":
                return " text/xml";
            case ".txt":
                return " text/plain";
            case ".uls":
                return " text/iuls";
            case ".vcf":
                return " text/x-vcard";
            case ".vml":
                return " text/xml";
            case ".vxml":
                return " text/xml";
            case ".wav":
                return " audio/wav";
            case ".wax":
                return " audio/x-ms-wax";
            case ".wbmp":
                return " image/vnd.wap.wbmp";
            case ".wm":
                return " video/x-ms-wm";
            case ".wma":
                return " audio/x-ms-wma";
            case ".wml":
                return " text/vnd.wap.wml";
            case ".wmv":
                return " video/x-ms-wmv";
            case ".wmx":
                return " video/x-ms-wmx";
            case ".wsc":
                return " text/scriptlet";
            case ".wsdl":
                return " text/xml";
            case ".wvx":
                return " video/x-ms-wvx";
            case ".xdr":
                return " text/xml";
            case ".xq":
                return " text/xml";
            case ".xql":
                return " text/xml";
            case ".xquery":
                return " text/xml";
            case ".xsd":
                return " text/xml";
            case ".xsl":
                return " text/xml";
            case ".xslt":
                return " text/xml";
            default:
                return "image/jpeg";
        }
    }

    /**
     * 字节转kb/mb/gb
     *
     * @param size
     * @return
     */
    private static String getPrintSize(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
        }
    }
}
