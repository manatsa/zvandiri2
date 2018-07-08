<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item">
                            <form:hidden path="catDetail" value="${item.catDetail.id}"/>
                            <form:hidden path="period" value="${item.period.id}"/>
                            <%@include file="../template/formState.jspf" %>
                            <div class="form-group">
                                <label>Date of Visit</label>
                                <form:input path="dateOfVisit" class="form-control general"/>
                                <p class="help-block">
                                    <form:errors path="dateOfVisit" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Client</label>
                                <form:select path="patient" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${patients}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="patient" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Comment</label>
                                <form:textarea rows="4" path="comments" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="comments" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href=""><button class="btn btn-primary" type="button">Cancel</button></a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>