(function($) {
  $.fn.jfSidebar = function(options) {
    var opts = $.extend({}, $.fn.jfSidebar.defaults, options);
    return this.each(function() {
      $this = $(this);
      $this.find('li > a').each(function(i,item) {
        var a = $(this);
        var idx = i + 1;
        a.data("tabIndex", idx);
        a.attr("id", "a_" + idx);
        a.click(function () {
            active(a, opts);
            return false;
        }).dblclick(function() {
            active(a, opts);
            return false;
        });
      });
    });
  };
  function active(a,opts) {
    if (!a.next().hasClass('sub-menu')) {
      clickMenu(a,opts);
      return false;
    }
    var parent = a.parent().parent();
    parent.children('li.open').children('a').children('.arrow').removeClass('open');
    parent.children('li.open').children('.sub-menu').slideUp(200);
    parent.children('li.open').removeClass('open');
    var sub = a.next();
    if (sub.is(":visible")) {
      jQuery('.arrow', a).removeClass("open");
      a.parent().removeClass("open");
      sub.slideUp(200, function () {});
    } else {
      jQuery('.arrow', a).addClass("open");
      a.parent().addClass("open");
      sub.slideDown(200, function () {});
    }
  };
  function clickMenu(a,opts) {
    var doClick = opts.clickEvent;
    if (!doClick) {
      return false;
    }
    doClick(a);
  };
  $.fn.jfSidebar.defaults = {
    clickEvent: function(a) {
      if (a) {
        alert(a.text().replace(/(\s+)/ig,''));
      } else {
        alert("no title");
      }
    }
  };
})(jQuery);