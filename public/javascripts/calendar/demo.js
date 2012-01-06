$(document).ready(function() {

    var id = 1;
    var $calendar = $('#calendar');    

    $calendar.weekCalendar({
        displayOddEven:true,
        timeslotsPerHour : 4,
        allowCalEventOverlap : true,
        overlapEventsSeparate: true,
        firstDayOfWeek : 1,
        businessHours :{
            start: 7, 
            end: 18, 
            limitDisplay: true
        },
        daysToShow : 7,
        switchDisplay: {
            '1 ngày': 1, 
            '3 ngày tới': 3, 
            'Ngày làm việc': 5, 
            'Cả tuần': 7
        },
        title: function(daysToShow) {
            return daysToShow == 1 ? '%date%' : '%start% - %end%';
        },
        height : function($calendar) {
            return $(window).height() - $("h1").outerHeight() - 1;
        },
        eventRender : function(calEvent, $event) {
            if (calEvent.end.getTime() < new Date().getTime()) {
                $event.css("backgroundColor", "#aaa");
                $event.find(".wc-time").css({
                    "backgroundColor" : "#999",
                    "border" : "1px solid #888"
                });
            }
        },
        draggable : function(calEvent, $event) {            
            return calEvent.readOnly != true;
        },
        resizable : function(calEvent, $event) {
            return calEvent.readOnly != true;
        },
        eventNew : function(calEvent, $event) {
            var $dialogContent = $("#event_edit_container");
            resetForm($dialogContent);
            var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
            var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
            var noidungField = $dialogContent.find("textarea[name='noidung']");
            var thanhphanField = $dialogContent.find("input[name='thanhphan']");
            var chutriField = $dialogContent.find("input[name='chutri']");
            var diadiemField = $dialogContent.find("input[name='diadiem']");

            $dialogContent.dialog({
                modal: true,
                title: "Lịch công việc mới",
                close: function() {
                    $dialogContent.dialog("destroy");
                    $dialogContent.hide();
                    $('#calendar').weekCalendar("removeUnsavedEvents");
                },
                buttons: {
                    "Lưu" : function() {
                        calEvent.id = id;
                        id++;
                        calEvent.start = new Date(startField.val());
                        calEvent.end = new Date(endField.val());
                        calEvent.noidung = noidungField.val();
                        calEvent.thanhphan = thanhphanField.val();
                        calEvent.chutri = chutriField.val();
                        calEvent.diadiem = diadiemField.val();
                        
                        $.ajax({  
                            type: "POST",  
                            url: "/QuanLyCongViec/nhapLich",  
                            data: JSON.stringify(calEvent)                           
                        });
                        
                        $calendar.weekCalendar("removeUnsavedEvents");
                        $calendar.weekCalendar("updateEvent", calEvent);
                        $dialogContent.dialog("close");
                    },
                    "Bỏ qua" : function() {
                        $dialogContent.dialog("close");
                    }
                }
            }).show();

            $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
            setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));

        },
        eventDrop : function(calEvent, $event) {                              
            $.ajax({  
                type: "POST",  
                url: "/QuanLyCongViec/suaLich",  
                data: JSON.stringify(calEvent)
            });
        },
        eventResize : function(calEvent, $event) {
            $.ajax({  
                type: "POST",  
                url: "/QuanLyCongViec/suaLich",  
                data: JSON.stringify(calEvent)
            });
        },
        eventClick : function(calEvent, $event) {

            if (calEvent.readOnly) {
                return;
            }
            
            var $dialogContent2 = $("#event_view_container");
            resetForm($dialogContent2);
            var startText = $dialogContent2.find("span[id='start']").text(calEvent.start.toString().substring(15, 21));
            var endText = $dialogContent2.find("span[id='end']").text(calEvent.end.toString().substring(15, 21));
            var noidungText = $dialogContent2.find("span[id='noidung']");
            noidungText.text(calEvent.noidung);
            var thanhphanText = $dialogContent2.find("span[id='thanhphan']").text(calEvent.thanhphan);
            var chutriText = $dialogContent2.find("span[id='chutri']").text(calEvent.chutri);
            var diadiemText = $dialogContent2.find("span[id='diadiem']").text(calEvent.diadiem);    

            $dialogContent2.dialog({
                modal: true,
                title: "Xem lịch công việc",
                close: function() {
                    $dialogContent2.dialog("destroy");
                    $dialogContent2.hide();
                    $('#calendar').weekCalendar("removeUnsavedEvents");
                },
                buttons: {
                    "Sửa" : function() {  
                        $dialogContent2.dialog("destroy");
                        $dialogContent2.hide();
                        var $dialogContent = $("#event_edit_container");
                        resetForm($dialogContent);
                        var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
                        var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
                        var noidungField = $dialogContent.find("textarea[name='noidung']");
                        noidungField.val(calEvent.noidung);
                        var thanhphanField = $dialogContent.find("input[name='thanhphan']").val(calEvent.thanhphan);
                        var chutriField = $dialogContent.find("input[name='chutri']").val(calEvent.chutri);
                        var diadiemField = $dialogContent.find("input[name='diadiem']").val(calEvent.diadiem); 

                        $dialogContent.dialog({
                            modal: true,
                            title: "Sửa lịch công việc",
                            close: function() {
                                $dialogContent.dialog("destroy");
                                $dialogContent.hide();
                                $('#calendar').weekCalendar("removeUnsavedEvents");
                            },
                            buttons: {
                                "Lưu" : function() {                                    
                                    calEvent.start = new Date(startField.val());
                                    calEvent.end = new Date(endField.val());
                                    calEvent.noidung = noidungField.val();
                                    calEvent.thanhphan = thanhphanField.val();
                                    calEvent.chutri = chutriField.val();
                                    calEvent.diadiem = diadiemField.val();
                                    $.ajax({  
                                        type: "POST",  
                                        url: "/QuanLyCongViec/suaLich",  
                                        data: JSON.stringify(calEvent) 
                                    });
                                    $calendar.weekCalendar("updateEvent", calEvent);
                                    $dialogContent.dialog("close");
                                },                                                                
                                "Bỏ qua" : function() {
                                    $dialogContent.dialog("close");
                                }
                            }
                        }).show();     
                        
                        var startField = $dialogContent.find("select[name='start']").val(calEvent.start);
                        var endField = $dialogContent.find("select[name='end']").val(calEvent.end);
                        $dialogContent.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
                        setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));
                        $(window).resize().resize(); //fixes a bug in modal overlay size ??
                    },
                    "Xóa" : function() {
                        $.ajax({  
                            type: "POST",  
                            url: "/QuanLyCongViec/xoaLich",  
                            data: calEvent.id.toString()
                        });
                                    
                        $calendar.weekCalendar("removeEvent", calEvent.id);
                        $dialogContent2.dialog("close");
                    },
                    "Bỏ qua" : function() {
                        $dialogContent2.dialog("close");
                    }                    
                }
            }).show();     
            $dialogContent2.find(".date_holder").text($calendar.weekCalendar("formatDate", calEvent.start));
            $(window).resize().resize();
        },
        eventMouseover : function(calEvent, $event) {
        },
        eventMouseout : function(calEvent, $event) {
        },
        noEvents : function() {

        },
        data : function(start, end, callback) {
            $.getJSON('/quanlycongviec/JSONLich', {
                start: start.getTime(),
                end: end.getTime()
            },
            function(result) {
                if (result != null) {
                    for (i in result) {
                        var calEvent = result[i];
                        calEvent.userId = parseInt(calEvent.userId);
                    }
                }
                var calevents = result;
                callback(calevents);
            });
        }
    });

    function resetForm($dialogContent) {
        $dialogContent.find("input").val("");
        $dialogContent.find("textarea").val("");
    }   

    /*
    * Sets up the start and end time fields in the calendar event
    * form for editing based on the calendar event being edited
    */
    function setupStartAndEndTimeFields($startTimeField, $endTimeField, calEvent, timeslotTimes) {

        $startTimeField.empty();
        $endTimeField.empty();

        for (var i = 0; i < timeslotTimes.length; i++) {
            var startTime = timeslotTimes[i].start;
            var endTime = timeslotTimes[i].end;
            var startSelected = "";
            if (startTime.getTime() === calEvent.start.getTime()) {
                startSelected = "selected=\"selected\"";
            }
            var endSelected = "";
            if (endTime.getTime() === calEvent.end.getTime()) {
                endSelected = "selected=\"selected\"";
            }
            $startTimeField.append("<option value=\"" + startTime + "\" " + startSelected + ">" + timeslotTimes[i].startFormatted + "</option>");
            $endTimeField.append("<option value=\"" + endTime + "\" " + endSelected + ">" + timeslotTimes[i].endFormatted + "</option>");

            $timestampsOfOptions.start[timeslotTimes[i].startFormatted] = startTime.getTime();
            $timestampsOfOptions.end[timeslotTimes[i].endFormatted] = endTime.getTime();

        }
        $endTimeOptions = $endTimeField.find("option");
        $startTimeField.trigger("change");
    }

    var $endTimeField = $("select[name='end']");
    var $endTimeOptions = $endTimeField.find("option");
    var $timestampsOfOptions = {
        start:[],
        end:[]
    };

    //reduces the end time options to be only after the start time options.
    $("select[name='start']").change(function() {
        var startTime = $timestampsOfOptions.start[$(this).find(":selected").text()];
        var currentEndTime = $endTimeField.find("option:selected").val();
        $endTimeField.html(
            $endTimeOptions.filter(function() {
                return startTime < $timestampsOfOptions.end[$(this).text()];
            })
            );

        var endTimeSelected = false;
        $endTimeField.find("option").each(function() {
            if ($(this).val() === currentEndTime) {
                $(this).attr("selected", "selected");
                endTimeSelected = true;
                return false;
            }
        });

        if (!endTimeSelected) {
            //automatically select an end date 2 slots away.
            $endTimeField.find("option:eq(1)").attr("selected", "selected");
        }

    });          
});
