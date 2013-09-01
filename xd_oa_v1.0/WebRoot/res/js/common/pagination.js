/**
 * guoyfc
 * 作用：分页
 * 配合Pagination.java共同使用
 * @param page 将Pagination对象转换成json格式就是一个page.
 * @return
 */
function createPage(page) {
	var autoCount = page.autoCount;
	var first = page.first;
	var hasNext = page.hasNext;
	var hasPre = page.hasPre;
	var nextPage = page.nextPage;
	var order = page.order;
	var orderBy = page.orderBy;
	var orderBySetted = page.orderBySetted;
	var pageNo = page.pageNo;
	var pageSize = page.pageSize;
	var prePage = page.prePage;
	var totalCount = page.totalCount;
	var totalPages = page.totalPages;
	var html = "<div class='page-list-3 pdt-10'>";
	if (pageNo == 1) {
		html += "<a href='###' class='last-page'>首页</a>";
	} else {
		html += "<a href='###' onclick='firstPage()'>首页</a>";
	}
	if (hasPre) {
		html += "<a href='###' onclick='prePage()'>上一页</a>";
	} else {
		html += "<a href='###' class='last-page'>上一页</a>";
	}
	if (hasNext) {
		html += "<a href='###' onclick='nextPage()'>下一页</a>";
	} else {
		html += "<a href='###' class='last-page'>下一页</a>";
	}
	if (pageNo == totalPages||totalPages==0) {
		html += "<a href='###' class='last-page'>尾页</a>";
	} else {
		html += "<a href='###' onclick='lastPage("+totalPages+")'>尾页</a>";
	}
	
	html += "<a>共" + totalPages
			+ " 页</a><a>当前第 " + pageNo + "/"
			+ totalPages + " 页</a><a>共 "
			+ totalCount + " 条记录</a></div>";
	return html;
}

function createPage1(page) {
	var autoCount1 = page.autoCount;
	var first1 = page.first;
	var hasNext1 = page.hasNext;
	var hasPre1 = page.hasPre;
	var nextPage1 = page.nextPage;
	var order1 = page.order;
	var orderBy1 = page.orderBy;
	var orderBySetted1 = page.orderBySetted;
	var pageNo1 = page.pageNo;
	var pageSize1 = page.pageSize;
	var prePage1 = page.prePage;
	var totalCount1 = page.totalCount;
	var totalPages1 = page.totalPages;
	var html = "<div class='page-list-3 pdt-10'>";
	if (pageNo1 == 1) {
		html += "<a href='###' class='last-page'>首页</a>";
	} else {
		html += "<a href='###' onclick='firstPage1()'>首页</a>";
	}
	if (hasPre1) {
		html += "<a href='###' onclick='prePage1()'>上一页</a>";
	} else {
		html += "<a href='###' class='last-page'>上一页</a>";
	}
	if (hasNext1) {
		html += "<a href='###' onclick='nextPage1()'>下一页</a>";
	} else {
		html += "<a href='###' class='last-page'>下一页</a>";
	}
	if (pageNo1 == totalPages1||totalPages1==0) {
		html += "<a href='###' class='last-page'>尾页</a>";
	} else {
		html += "<a href='###' onclick='lastPage("+totalPages1+")'>尾页</a>";
	}
	
	html += "<a>共" + totalPages1
			+ " 页</a><a>当前第 " + pageNo1 + "/"
			+ totalPages1 + " 页</a><a>共 "
			+ totalCount1 + " 条记录</a></div>";
	return html;
}
