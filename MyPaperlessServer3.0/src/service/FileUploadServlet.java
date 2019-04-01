package service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 文件上傳
 * 
 * @author NSD
 * 
 */
public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileUploadServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			// 设置字符编码为UTF-8, 这样支持汉字显示
			response.setCharacterEncoding("UTF-8");
			final long MAX_SIZE = 30 * 1024 * 1024;// 设置上传文件最大为 30M
			// 允许上传的文件格式的列表
			final String[] allowedExt = new String[] { "jpg", "jpeg", "gif",
					"txt", "doc", "docx", "mp3", "wma", "m4a", "png" };
			// 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			dfif.setSizeThreshold(1024 * 1024 * 30);// 设置上传文件时用于临时存放文件的内存大小,这里是30M.
			// 设置存放临时文件的目录,web根目录下的UploadTemp目录
			dfif.setRepository(new File(request.getSession()
					.getServletContext().getRealPath("/uploadTemp")));
			// 用以上工厂实例化上传组件
			ServletFileUpload sfu = new ServletFileUpload(dfif);
			// 设置最大上传尺寸
			sfu.setSizeMax(MAX_SIZE);
			// 从request得到 所有 上传域的列表
			List<FileItem> fileList = null;
			fileList = sfu.parseRequest(request);
			// 没有文件上传
			if (fileList == null || fileList.size() == 0) {
				System.out.println("请选择上传文件");
				return;
			}
			// 得到所有上传的文件
			Iterator<FileItem> fileItr = fileList.iterator();
			// 循环处理所有文件
			while (fileItr.hasNext()) {
				FileItem fileItem = null;
				String path = null;
				long size = 0;
				// 得到当前文件
				fileItem = (FileItem) fileItr.next();
				// 忽略简单form字段而不是上传域的文件域(<input type="text" />等)
				if (fileItem == null || fileItem.isFormField()) {
					continue;
				}
				// 得到文件的完整路径
				path = fileItem.getName();
				// 得到文件的大小
				size = fileItem.getSize();
				if ("".equals(path) || size == 0) {
					System.out.println("请选择上传文件");
					return;
				}
				// 得到去除路径的文件名
				String t_name = path.substring(path.lastIndexOf("\\") + 1);
				System.out.println("文件名:" + path);
				byte[] b1 = path.getBytes("GBK");
				String str = new String(b1, 0, b1.length);
				System.out.println("文件名-:" + str);
				// 得到文件的扩展名(无扩展名时将得到全名)
				String t_ext = t_name.substring(t_name.lastIndexOf(".") + 1);
				// 拒绝接受规定文件格式之外的文件类型
				int allowFlag = 0;
				int allowedExtCount = allowedExt.length;
				for (; allowFlag < allowedExtCount; allowFlag++) {
					if (allowedExt[allowFlag].equals(t_ext))
						break;
				}
				// 圖片類型不符合要求
				if (allowFlag == allowedExtCount) {
					System.out.println("請上傳以下類型文件<p />");
					for (allowFlag = 0; allowFlag < allowedExtCount; allowFlag++)
						System.out.println("*." + allowedExt[allowFlag]
								+ "&nbsp;&nbsp;&nbsp;");
					return;
				}
				// 保存的最终文件完整路径,保存在web根目录下的Image目录下
				String u_name = request.getSession().getServletContext()
						.getRealPath("/image/" + t_name);
				System.out.println("最后保存的路徑：" + u_name);
				// 保存文件
				fileItem.write(new File(u_name));
				System.out.println("文件上傳成功，保存格式： " + "." + t_ext + ";大小："
						+ size + "KB");
			}
		} catch (Exception e) {// 处理文件尺寸过大异常
			e.printStackTrace();

		}
	}

	public void init() throws ServletException {}

}
