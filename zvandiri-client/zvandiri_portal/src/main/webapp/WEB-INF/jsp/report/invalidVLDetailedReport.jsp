<%@include file="../template/header.jspf"%>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">${pageTitle}</div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To
                Reports DashBoard Home</a><br /> <span class="text-error right" style="text-align: right;">Invisible columns will be visible in exported data!</span>

            <form:form  role="form" modelAttribute="item" method="post">
                <div class="row">
                    <div class="alert alert-danger">
                        <span>Please make sure to define the shortest period you want to check by selecting start date and end date.</span>
                    </div>
                </div>
                <table class="table">
                    <tbody>
                    <tr>
                        <c:if test="${userLevel == 'NATIONAL'}">
                            <td>
                                <div class="form-group">
                                    <label>Region</label>
                                    <form:select path="province" class="form-control">
                                        <form:option value="" label="--Select Item--"/>
                                        <form:options items="${provinces}" itemLabel="name" itemValue="id"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="province"/>
                                    </p>
                                </div>
                            </td>
                        </c:if>
                        <c:if test="${userLevel == 'NATIONAL' or userLevel == 'PROVINCE'}">
                            <td>
                                <div class="form-group">
                                    <label>District</label>
                                    <form:select path="district" class="form-control">
                                        <form:option value="" label="--Select Item--"/>
                                        <form:options items="${districts}" itemLabel="name" itemValue="id"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="district"/>
                                    </p>
                                </div>
                            </td>
                        </c:if>
                        <c:if test="${userLevel == 'NATIONAL' or userLevel == 'PROVINCE'}">
                            <td>
                                <div class="form-group">
                                    <label>Facility</label>
                                    <form:select path="primaryClinic" class="form-control">
                                        <form:option value="" label="--Select Item--"/>
                                        <form:options items="${facilities}" itemLabel="name" itemValue="id"/>
                                    </form:select>
                                    <p class="help-block">
                                        <form:errors path="primaryClinic"/>
                                    </p>
                                </div>
                            </td>
                        </c:if>
                        <td>
                            <div class="form-group">
                                <label>Gender</label>
                                <form:select path="gender" class="form-control">
                                    <form:option value="" label="--Select Item--"/>
                                    <form:options itemLabel="name" itemValue="code"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="gender"/>
                                </p>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <label>Status</label>
                                <form:select path="status" class="form-control">
                                    <form:option value="" label="--Select Item--"/>
                                    <form:options itemLabel="name" itemValue="code"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="status"/>
                                </p>
                            </div>
                        </td>

                        <td>
                            <div class="form-group">
                                <label>&nbsp;</label><br/>
                                <button class="btn btn-primary" type="submit">Search</button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form:form>


            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>Age</th>
<%--                        <th>OI/ ART Number</th>--%>
<%--                        <th>Date of Birth</th>--%>
                        <th>Gender</th>
                        <th>IsCATS</th>
<%--                        <th>Region</th>--%>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Date Of Death</th>
                        <th>Cause Of Death</th>
                        <th>Cause of Death Details</th>
                        <th>Received Enhanced Care</th>
                        <th>Date Put On Enhanced Care</th>
                        <th>Case Background</th>
                        <th>Care Provided</th>
                        <th>Learning Points</th>
                        <th>Action Plan</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>Age</th>
<%--                        <th>OI/ ART Number</th>--%>
<%--                        <th>Date of Birth</th>--%>
                        <th>Gender</th>
                        <th>IsCATS</th>
<%--                        <th>Region</th>--%>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Date Of Death</th>
                        <th>Cause Of Death</th>
                        <th>Cause of Death Details</th>
                        <th>Received Enhanced Care</th>
                        <th>Date Put On Enhanced Care</th>
                        <th>Case Background</th>
                        <th>Care Provided</th>
                        <th>Learning Points</th>
                        <th>Action Plan</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td>${item.patient.name}</td>
                                <td>${item.patient.age}</td>

<%--                                <td>${item.patient.oINumber}</td>--%>
<%--                                <td><spring:eval expression="item.patient.dateOfBirth" /></td>--%>
                                <td>${item.patient.gender.name}</td>
                                <td>${item.patient.cat.name}</td>
<%--                                <td>${item.patient.primaryClinic.district.province.name}</td>--%>
                                <td>${item.patient.primaryClinic.district.name}</td>
                                <td>${item.patient.primaryClinic.name}</td>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <div class="panel-footer" style="text-align: right">
            Export/ View As <a href="${page}${excelExport}"> <img
                src="<c:url value="/resources/images/excel.jpeg"/>" />
        </a>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf"%>
<script type="text/javascript">
    $(".sidebar-nav").addClass("custom-side-bar-ref");
    $("#page-wrapper").addClass("main-wrp");
    // ensire toggle side bar is pointing right
    $("span.toggle-span").addClass("fa-long-arrow-right");
    $("span.toggle-span").removeClass("fa-long-arrow-left");
</script>
