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
                        <div class="panel panel-default">                            
                            <%@include file="../template/dashboard/patientProfile.jspf" %>
                        </div>
                    </div>
                </div>
                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back To ${patient.name} Dashboard</a><br/><br/>
                <div class="row">
                    <div class="col-lg-10">
                        <form:form commandName="item">
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="patient" value="${item.patient.id}"/>    
                            <div class="form-group">
                                <label>Screened For Mental Health</label>
                                <form:select path="screenedForMentalHealth" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="screenedForMentalHealth" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Identified Risks</label>
                                <form:select path="identifiedRisk" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${risks}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="identifiedRisk" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Action Taken</label>
                                <form:select path="actionTaken" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${actionTakens}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="actionTaken" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Other</label>
                                <form:input path="actionTakenText" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="actionTakenText" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Rescreened For Mental Health</label>
                                <form:select path="mentalScreenResult" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${results}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="mentalScreenResult" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group riskDiv hide">
                                <label>Identified Risks</label>
                                <form:select path="rescreenIdentifiedRisk" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${risks}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="rescreenIdentifiedRisk" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group actDiv hide">
                                <label>Action Taken</label>
                                <form:select path="rescreenActionTaken" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${actionTakens}" itemValue="id" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="rescreenActionTaken" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <c:if test="${canEdit}"><button class="btn btn-primary" type="submit">Save</button></c:if>
                                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}"><button class="btn btn-primary" type="button">Cancel</button></a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    $("#mentalScreenResult").change(function(){
       var name = $("#mentalScreenResult").val();
       console.log(name);
       if(name === "1"){
           $(".actDiv").removeClass("hide");
           $(".riskDiv").addClass("hide");
       }else if(name === "2"){
           $(".actDiv").removeClass("hide");
           $(".riskDiv").removeClass("hide");
       }else{
           $(".actDiv").addClass("hide");
           $(".riskDiv").addClass("hide");
       }
    });
</script>