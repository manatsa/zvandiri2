<%@include file="../template/header.jspf" %>
<%@include file="../template/searchDateRangeNewFragment.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <div class="row">
                    <!--Start of top left panel -->
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                ${reportTitle}
                            </div>
                            <div class="panel-body">
                                <img class="img-responsive" src="${page}/report/graphs${report}"/>
                            </div>  
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@include file="../template/footer.jspf" %>