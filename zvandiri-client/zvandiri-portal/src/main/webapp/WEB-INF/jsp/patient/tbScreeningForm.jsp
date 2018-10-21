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
                        <form:form commandName="item" action="${formAction}">
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="person" value="${item.person.id}"/>
                            <label>TB Screening Questions</label>
                            <div class="form-group">
                                <label>Are you coughing?</label>
                                <form:select path="coughing" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="coughing" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Are you sweating?</label>
                                <form:select path="sweating" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="sweating" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Have you had Unintentional weight loss in last 6months?</label>
                                <form:select path="weightLoss" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="weightLoss" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Have you had a fever ?</label>
                                <form:select path="fever" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="fever" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Currently On TB Treatment</label>
                                <form:select path="currentlyOnTreatment" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${yesNo}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="currentlyOnTreatment" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>Type Of Drug</label>
                                <form:input path="typeOfDrug" class="form-control"/>
                                <p class="help-block">
                                    <form:errors path="typeOfDrug" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>TB Outcome</label>
                                <form:select path="tbOutcome" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${results}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="tbOutcome" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>TB Contacts Page</label>
                                <form:select path="tbTreatmentStatus" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${status}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="tbTreatmentStatus" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <label>TB Treatment Outcome</label>
                                <form:select path="tbTreatmentOutcome" class="form-control">
                                    <form:option value="" label="--Select Item"/>
                                    <form:options items="${outcomes}" itemValue="code" itemLabel="name"/>
                                </form:select>
                                <p class="help-block">
                                    <form:errors path="tbTreatmentOutcome" class="alert-danger"/>
                                </p>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="#"><button class="btn btn-primary" type="button">Cancel</button></a>
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
</script>