/**
 * Mobile Support Only,don't use it in IE
 * Created by jale on 14-4-22.
 */
(function () {
    var debug = false;
    var I = {
        i: function (info) {
            debug && console.info('[Info]' + info);
        },
        e: function (info) {
            debug && console.error('[Error]' + info);
        }
    };

    /**
     * add trim method for String
     */
    String.prototype.trim = function() {
        return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
    };


    var D = document, W = window, $, $c = {}, $j = '__jamObject';
    var $pf = parseFloat, $hp = 'hasOwnProperty';
    /**
     * Init like jQuery method
     */
    var jam = $ = W.jam = W.$$ = function (expr) {
        if (expr.jamObjects) {
            return expr;
        }
        var t = typeof expr, $jam;
        if (t == 'function') {
            I.i('[jam init]callback..');
            return $(D).ready(expr);
        } else if (t == 'string') {
            $jam = $c[expr];
        } else if (t == 'object') {
            $jam = expr[$j];
        }
        return $jam || new jam.fn.init(expr);
    };
    jam.fn = jam.prototype = {
        constructor: jam,
        init: function (expr) {
            I.i('[jam init]New..');
            var $i = this, t = typeof expr;
            var sels = t == 'string' ? Array.prototype.slice.call(D.querySelectorAll(expr)) : expr.length !== undefined ? expr : [expr];
            this.jamObjects = sels;
            for (var i = 0; i < $i.jamObjects.length; i++)
                $i[i] = $i.jamObjects[i];
            this.length = sels.length;
            this.get = function (index) {
                return $($i[index]);
            };
            this.eq = function (index) {
                return $i.jamObjects[index];
            };
            if (sels.length == 1) { // if object length > 0,the object will not be cached
                sels[0][$j] = $i;
                t == 'string' && t.charAt(0) == '#' && ($c[expr] = $i);
            }
            t == 'string' && ($i.expr = t);
            return $i;
        }
    };
    jam.fn.init.prototype = jam.fn;

    /**
     * @param expr
     */
    $.fn.add = function(expr) {
        var t = $(expr),$i = this,len = $i.jamObjects.length;
        for (var i = 0; i < t.jamObjects.length; i++) {
            $i.jamObjects.push(t.jamObjects[i]);
            $i[len + i] = $i.jamObjects[len + i];
        }
        $i.length = $i.jamObjects.length;
        return $i;
    };


    $.fn.parents = function(expr) {
        return $($fds(this,expr,'parentNode'));
    };

    $.fn.parentsUntil = function(expr) {
        return $($fdu(this,expr,'parentNode'));
    };

    /**
     * find the latest parent element match expr,if expr is null, the method will return the this.parentNode
     * @param expr
     */
    $.fn.parent = function(expr) {
        return $($fd(this,expr,'parentNode'));
    };

    $.fn.prev = function(expr) {
        return $($fd(this,expr,'previousSibling'));
    };

    $.fn.prevs = function(expr) {
        return $($fds(this,expr,'previousSibling'));
    };

    $.fn.next = function(expr) {
        return $($fd(this,expr,'nextSibling'));
    };

    $.fn.nexts = function(expr) {
        return $($fds(this,expr,'nextSibling'));
    };

    $.fn.siblings = function(expr) {
        return this.prevs(expr).add(this.nexts(expr));
    };

    function $fd(o,expr,attr) {
        var t = [];
        o.each(function(){
            var p = this;
            while((p = p[attr]) != D && p) {
                if(p.nodeType == 1 && (!expr || p.webkitMatchesSelector(expr))) {
                    t.push(p);
                    break;
                }
            }
        });
        return t;
    }

    function $fds(o,expr,attr) {
        var t = [];
        o.each(function(){
            var p = this;
            while((p = p[attr])!= D && p)
                p.nodeType == 1  && (!expr || p.webkitMatchesSelector(expr)) && t.push(p);
        });
        return t;
    }

    function $fdu(o,expr,attr) {
        var t = [];
        o.each(function(){
            var p = this;
            while((p = p[attr]) != D && p) {
                if(p.nodeType == 1 && p.webkitMatchesSelector(expr))
                    break;
                else if(p.nodeType == 1)
                    t.push(p);
            }
        });
        return $(t);
    }

    /**
     * data storage support
     * @param k
     * @param v
     */
    $.fn.data = function (k, v) {
        if (v) return this.data[k];
        return this.each(function () {
            !$(this).data && ($(this).data = {});
            $(this).data[k] = v;
        });
    };

    /**
     * get & set value
     */
    $.fn.val = function (v) {
        if(!this.length) return null;
        var $i = this[0], t = $i.tagName;
        if (arguments.length) {
            return this.each(function () {
                switch (t) {
                    case 'SELECT' :
                    {
                        this.find('option').each(function () {
                            var $i = this;
                            if ($i.value == v) {
                                $i.selected = true;
                                return false;
                            }
                            return true;
                        });
                        break;
                    }
                    default :
                    {
                        this.value = v;
                    }
                }
            });
        }
        switch (t) {
            case 'SELECT' :
            {
                var val = null;
                this.find('option').each(function () {
                    var $i = this;
                    if ($i.selected) {
                        val = $i.value;
                        return false;
                    }
                    return true;
                });
                return val;
            }
            default :
            {
                return $i.value;
            }
        }
    };

    /**
     * get & set text content
     * @param t
     */
    $.fn.text = function(t) {
        if(!arguments.length)
            return this[0].textContent;
        else
            this.each(function(){
                this.textContent = t;
            });
    };
    /**
     * search
     * not support expr ":selected"
     */
    $.fn.find = function (expr) {
        var objs = null;
        this.each(function(){
            var hasId = true;
            if (!this.id) {
                this.id = '_JAM_EXPR_ID_';
                hasId = false;
            }
            var s = $('#' + this.id + ' ' + expr);
            !objs ? (objs = s) : objs.add(s);
            !hasId && (this.id = null);
        });
        return objs;
    };

    /**
     * iterator
     * @param func
     * @returns {$.fn}
     */
    $.fn.each = function (func) {
        for (var i = 0; i < this.length; i++) {
            if (func.call(this[i]) === false) break;
        }
        return this;
    };

    /**
     * event support
     */
    var EventHandler = function (ev) {
        return function (func) {
            I.i('bind event ' + ev + ' to ' + this);
            return this.each(function () {
                this.addEventListener(ev, func, false);
            });
        }
    };

    function addEvent(events, names) {
        for (var i = 0; i < events.length; i++) {
            $.fn[names[i]] = new EventHandler(events[i]);
        }
    }

    addEvent(['mousedown','touchstart', 'touchmove', 'touchend', 'submit', 'click', 'load', 'focus', 'blur', 'DOMContentLoaded'],
             ['mousedown','touchStart', 'touchMove', 'touchEnd', 'submit', 'click', 'load', 'focus', 'blur', 'ready']);

    /**
     * delegate children event
     * @param expr
     * @param e
     * @param func
     */
    $.fn.delegate = function(expr,e,func) {
        return this[e](function(ev){
            $(this).find(expr).each(function(){
               if(this == ev.target) {
                   func.call(ev.target);
                   return false;
               }
            });
        });
    };

    /**
     * css edit support,this method would not do anything about attribute unit,you must define it by yourself
     * @param css
     */
    $.fn.css = function (css) {
        return this.each(function () {
            for (var i in css) {
                if (css[$hp](i)) {
                    this.style[i] = css[i];
                }
            }
        });
    };

    var apx = {left:1,right: 1,top: 1,bottom: 1};
    /**
     * size and position support
     */
//    for(var i in apx) {
//        $.fn[i] = function(px) {
//            if (px || px === 0) {
//                var nu = $pnu(px);
//                return this.each(function () {
//                    this.style[i] = (nu.n + (nu.u || 'px'));
//                });
//            } else
//                return $pf(this[0].style[i] || W.getComputedStyle(this[0])[i]);
//        }
//    }
    $.fn.width = function (px) {
        if (px || px === 0) {
            var nu = $pnu(px);
            return this.each(function () {
                this.style.width = (nu.n + (nu.u || 'px'));
            });
        } else {
            return $pf(this[0].style.width || W.getComputedStyle(this[0])['width']);
        }
    };
    $.fn.height = function (px) {
        if (px || px === 0) {
            var nu = $pnu(px);
            return this.each(function () {
                this.style.height = (nu.n + (nu.u || 'px'));
            });
        } else {
            return  Math.max(this[0].clientHeight, this[0].scrollHeight);
        }
    };

    /**
     * get attribute value,if length > 1 ,the return is an array
     * @param p
     * @returns {*}
     */
    $.fn.attr = function (p) {
        if (this.length == 1) {
            return this[0].getAttribute(p);
        } else {
            var a = [];
            this.each(function () {
                a.push(this.getAttribute(p));
            });
            return a;
        }
    };

    var a_dft = {
        method : 'GET'
        ,async : true
        ,timestamp : false //add a timestamp
    };
    /**
     * ajax support
     * @param cfg
     */
    $.ajax = function(cfg) {
        if(!cfg) return;
        cfg = $mg(cfg,a_dft);
        var xhr =new XMLHttpRequest();
        xhr.open(cfg.method,cfg.url,cfg.async);
        cfg.method.toLocaleLowerCase() == 'post' && xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        function callback() {
            if (xhr.readyState==4 && xhr.status==200)
                cfg.success(cfg.dataType == 'json' ? $.json(xhr.responseText) : xhr.responseText);
            else if (xhr.readyState==4)
                cfg.error(xhr.responseText);
        }
        cfg.async && (xhr.onreadystatechange=callback);
        xhr.send(cfg.data);
        !cfg.async && callback();
    };

    /**
     *
     * -------------------------------------------------------- animate begin -----------------------------------------------------------------------------------
     * animate support,this method is not support global chaining,
     * it has its own chain which like $(XX).animate().nextAnimate().nextAnimate()...start()...
     * ps:translate3d is not friendly enough to android
     * @param cfg
     * running state {
     *  -1 : not init,
     *  0 : stopped,
     *  1 : running
     *  2 : pausing
     *  3 : waiting sub animate
     * }
     *
     */
    var fps = 59, engine = null, frames = [], SIG_SKIP = 'skip';//

    function c_build(cfg, o) {
        return {
            id: o.attr('id'), //temp
            o: o,
            fp: 0,
            fn: Math.ceil(cfg.sec / (1000 / fps)),
            fd: [cfg.delay ? Math.ceil(cfg.delay / (1000 / fps)) : 0, 0], //delay time,if sub animate delay time did not set,the parent delay will be extended.
            fe: cfg.to,
            fs: cfg.from,
            ft: cfg.filter,
            u: cfg.upgradeFunc || ufunc,
            pu: cfg.useParentUpgradeFunc,
            cst: cfg.onStart,
            csp: cfg.onStop
        };
    }

    $.fn.animate = function (cfg) {
        var self = this;
        var ctx = c_build(cfg, self);

        !engine && (engine = W.setInterval(function () {
            for (var i = 0; i < frames.length; i++) {
                if(!doAnimate(frames[i])) {
                    if(frames[i].g.length && frames[i].ge < frames[i].g.length) {
                        frames[i].r = 3; //set state to waiting
                    }else
                        frames[i].stop();
                }
            }
        }, 1000 / fps));

        var amt = {
            id: ctx.id, //temp
            ctx: ctx,
            f: ctx.ft,
            init: function () {
                var $i = this;
                $i.ctx.fp = 0;
                $i.ctx.fd[1] = 0;
                $i.r = 0;
                $i.ge = 0;
                return $i;
            },
            /**
             * add a async animate
             * @param a
             */
            add: function (a) {
                this.g.push(a);
                a.$p = this;
                return this;
            },
            /**
             * add a sync animate
             * @param a
             */
            join: function (a) {
                this.n = a;
                a.p = this;
                return a;
            },
            /**
             * pause the current animation,insert a new animation and start it at once
             * when you need to insert a temperate animate,it's a good choice
             * @param cfg config
             * @param g use this config to all sub animate
             */
            insert: function (cfg, g) {
                var $i = this, gr = $i.g;
                $i.$ctx = $i.ctx;
                $i.$r = [$i.n,$i.r,$i.ge];
                var c = c_build(cfg, $i.ctx.o);
                c.csp = function () {
                    var $i = this;
                    $i.ctx = $i.$ctx; //swap the context
                    $i.n = $i.$r[0];
                    $i.ge = $i.$r[2];
                    $i.r = $i.$r[1];
                    cfg.onStop && cfg.onStop.call($i);
                };
                c.cst = null;
                c.pu = $i.$p ? c.pu : false; //
                $i.n = null;
                $i.ctx = c;
                !$i.$p && ($i.r = 1); //start at once
                if (g) { //sub animate
                    for (var i = 0; i < gr.length; i++)
                        gr[i].insert(cfg);
                }
                return this;
            },
            /**
             * start an animate group
             * @returns {$.fn.animate}
             */
            start: function () {
                var $i = this;
//                if ($i.$p) return $i;
                switch ($i.r) {
                    case 0:
                    {
                        $i.r = 1;
                        $i.ctx.o.show();
                        $i.ctx.fs && $mdc.call($i.ctx.o,$i.ctx.fs,this);
                        for (var i = 0; i < $i.g.length; i++) // set the style to <from>,if it has a value
                            $i.g[i].start();
                        $i.ctx.cst && $i.ctx.cst.call($i);
                        break;
                    }
                    case 2 :
                    {
                        $i.r = $i.fpp[1];
                        break;
                    }
                }
                return $i;
            },
            stop: function (a) {
                var $i = this;
                $i.init();
                $i.$p && $i.$p.ge++;
                !a && $i.r === 0 && $i.n && $i.n.start(); //force stop animate chain
                $i.ctx.csp && $i.ctx.csp.call($i);
                return $i;
            },
            pause: function () {
                var $i = this;
                if ($i.r === 1) {
                    I.i('animate pause!');
                    $i.fpp = [$i.ctx.fc, $i.r]; //save current css state and running state
                    $i.r = 2;
                }
                return this;
            },
            isRunning: function () {
                return this.r === 1;
            },
            r: 0, n: null, g: [], ge: 0, p: null, $p: null
        };
        frames.push(amt);
        return amt;
    };

    function ufunc(ctx, fn, fp) {
        var fc = {};
        if (fp > fn) return null;
        for (var i in ctx.fe) {
            var s , nu = $pnu(ctx.fe[i]);
            if (ctx.fe[$hp](i)) {
                s = ctx.fs && ctx.fs[$hp](i) ? $pnu(ctx.fs[i]) : {n: 0};
                fc[i] = (nu.a || '') + ((nu.n - s.n) * ((nu.a ? 1 : fp) / fn) + s.n);
                nu.u && (fc[i] += nu.u);
            }
        }
        return fc;
    }

    function doAnimate(atx) {
        var ctx = atx.ctx, fd = atx.$p ? atx.$p.ctx.fd : ctx.fd, pr = atx.$p ? atx.$p.r : 1;
        if (atx.r == 1 && (pr == 1 || pr == 3)) {
            if (fd[0] > 0 && fd[1] < fd[0]) {
                !atx.$p && ctx.fd[1]++;
                return true;
            }
            ctx.fp++;
            var fc = !ctx.pu ? ctx.u(ctx, ctx.fn, ctx.fp) : atx.$p.ctx.u(ctx, ctx.fn, ctx.fp);
            if (!fc) return false;
            fc !== SIG_SKIP && $mdc.call(ctx.o,fc,atx);
        }else if(atx.r == 3)
            return false;
        return true;

    }

    function $mdc (css,atx) {
        this.each(function () {
            var a = null, c = this.$cs, y;
            for (var i in css) {
                if (css[$hp](i)) {
                    !c && (c = this.$cs = {});
                    a = $pnu(css[i]);
                    if (a && a.a) {
                        y = c[i];
                        !y && (y = this.style[i]);
                        c[i] = ( $pf(a.n) + (y ? $pnu(y).n : 0) ) + a.u;
                    }else
                        c[i] = css[i];
                    (!atx || (atx && (!atx.f || atx.f(i,c[i])))) && (this.style[i] = c[i]);
                }
            }
        });
    }



    /**
     * -------------------------------------------------------- animate end -----------------------------------------------------------------------------------
     */


    /**
     * visible support
     * @returns {$.fn}
     */
    $.fn.show = function () {
        return this.each(function () {
            this.style.display = '';
        });
    };
    $.fn.hide = function () {
        return this.each(function () {
            this.style.display = 'none';
        });
    };

    /**
     * class add&remove support
     * @returns {$.fn}
     */
    $.fn.addClass = function (clazz) {
        return this.each(function () {
            this.classList.add(clazz);
        });
    };
    $.fn.removeClass = function (clazz) {
        return this.each(function () {
            this.classList.remove(clazz);
        });
    };
    $.fn.hasClass = function (clazz) {
        var hasIt = true;
        this.each(function () {
            hasIt = this.classList.contains(clazz);
            return hasIt;
        });
        return hasIt;
    };

    /**
     * dom edit support
     * PS:the method will returned the current object ,the child will be returned in jQuery
     * @param elem
     * @returns {*|Element}
     */
    $.fn.append = function (elem) {
        return this.each(function () {
            typeof elem == 'string' ? (this.innerHTML += elem) : this.appendChild(elem);
        });
    };

    /**
     * remove all children nodes
     * @returns {*|$.fn}
     */
    $.fn.empty = function () {
        return this.each(function () {
            this.innerHTML = '';
        });
    };

    /**
     * clone nodes
     * @returns {Array}
     */
    $.fn.clone = function () {
        var ns = [];
        this.each(function(){
            ns.push(this.cloneNode(true));
        });
        return ns;
    };

    $.fn.serialize = function() {

    };


    /**
     * -------------------------------------------------------- some utils -----------------------------------------------------------------------------------
     */

    //parseNumAndUnit
    function $pnu(val) {
        var a = /^(@?)([+-.0-9e]+)([a-zA-Z%]*)$/.exec(val);
        a == null && I.i("Can't parse value " + val);
        return a ? {a: a[1], n: $pf(a[2]), u: a[3]} : null;
    }

    //merge
    function $mg(dest, src) {
        for (var i in src) {
            if (src[$hp](i)) {
                var d = dest[i], s = src[i];
                d === undefined && (dest[i] = s);
            }
        }
        return dest;
    }

    /**
     * object to json or json to object
     * @param s
     */
    $.json = function(s) {
        if(typeof s == 'string') {
            var t;
            eval( 't = ' + s);
            return t;
        }else
            return $po(s);

    };

    function $po(o) {
        if(!o) return null;
        var k = 0, z = [],s = !o.length && o.length !== 0 ? ['{','}'] : ['[',']','{','}'];
        z.push(s[0]);
        for(var i in o) {
            var v = o[i], t = typeof v;
            k > 0 && z.push(',');
            s.length == 2 && z.push('"' + i + '"' + ':');
            z.push(t == 'object' ? $po(v) : t == 'string' ? ('"' + v + '"') : v);
            k++;
        }
        z.push(s[1]);
        return z.join('');
    }

    /**
     * save data in localStorage
     * @param k
     * @param v
     */
    $.local = function(k,v) {
        if(arguments.length == 2)
            W.localStorage[k] = $.json(v);
        else
            return $.json(W.localStorage[k]);
    };


    /**
     * carousel support touch operation
     * -------------------------------------------------------- carousel begin -----------------------------------------------------------------------------------
     * default config
     */
    var csrDict = {'fast': 500, 'normal': 800, 'slow': 1500, 'long': 5000, 'common': 3000, 'short': 1000};
    var defaultConfig = {
        moveSpeed: 'fast',
        stopTime: 'common',
        length: 0,
        p: 0
    };
    $.fn.carouselInit = function (opt) {
        return this.each(function () {
            var self = this, $i = $(this), items = [];
            opt = $mg(opt || {}, defaultConfig);
            $i.find('.item').each(function () {
                items.push($(this));
            });
            opt.items = items;
            self.crsConfig = opt;
            if (items.length > 1) {
                $i.css({
                    position: 'relative',
                    overflow: 'hidden'
                })/*.touchStart(function (ev) {
                    ev.preventDefault();
                    var $i = this , f = $i.crsConfig.$i, px = ev.targetTouches[0].pageX;
                    if($i.rec) return;
                    $i.$mvt = [px, px, 0, px];
                    f.insert({
                        upgradeFunc: function (ctx) {
                            return $mv.call($i, ctx);
                        },
                        onStop: function () {
                            if (this.$p) return;
                            var iw = this.ctx.o.width(), t = $i.$mvt, l = t[3] - t[1], r = l / iw;
                            var lt = $pnu(this.ctx.o[0].style.left).n;
                            var le2 = r > 0.5 ? -(iw + lt) : r < -0.5 ? iw - lt : l;
                            $i.rec = 1;
                            this.insert({
                                sec: 100,
                                to: {left: '@' + le2 + 'px'},
                                onStop: function() {
                                    if(r > 0.5)  // to next
                                        this.stop(true).n.start();
                                    else if(r < -0.5)  // to prev
                                        this.stop(true).p.start();
                                    $i.rec = 0;
                                }
                            });
                        }
                    });
                }).touchEnd(function (ev) {
                    ev.preventDefault();
                    var $i = this, f = $i.crsConfig.$i, fx = f.ctx;
                    $i.$mvt[0] = -1;
                }).touchMove(function (ev) {
                    ev.preventDefault();
                    var t = ev.targetTouches[0], $i = this;
                    $i.$mvt[1] = t.pageX;
                })*/;
                self.crsConfig.frames = doCrsInit(self.crsConfig);
                self.crsConfig.frames[0].start();
            }
        });
    };

    function $mv() {
        var $i = this, t = $i.$mvt, tf = t[1] - t[0];
        t[2]++;
        if (t[0] === -1) return null; //stop
        if (!tf) return SIG_SKIP;
        t[0] = t[1];
        return {left: '@' + tf + 'px'};
    }

    /**
     * add frames
     * @param cfg
     */
    function doCrsInit(cfg) {
        var it, frames = [], sec = csrDict[cfg.moveSpeed], ws = csrDict[cfg.stopTime], len = cfg.items.length;
        for (var i = 0; i < len; i++) {
            it = cfg.items[i];
            var iw = it.width();
            it.css({position: 'absolute',top: 0, left: (i == 0 ? 0 : -iw) + 'px'});
            var a = it.animate({
                from: { left: '0px'},
                to: { left: -iw + 'px'},
                sec: sec, delay: ws,
                onStart: function () {
                    cfg.showCaption && cfg.showCaption(this.$idx);
                    cfg.$i = this;
                },
                filter: function(n,i) {
                    var idx = this.$idx,y = $pnu(i),w = this.ctx.o.width();
                    var pt = idx == 0 ? cfg.items[len - 1] : cfg.items[idx -1], nt = idx == len - 1 ? cfg.items[0] : cfg.items[idx + 1];
                    y.n > 0 ? $mdc.call($(pt),{left : (y.n - w) + 'px'}) : $mdc.call($(nt),{left : (w + y.n) + 'px'});
                    return true;
                }

            });
            a.$idx = i;
            var fl = frames.length - 1;
            fl >= 0 && frames[fl].join(a);
            i + 1 == len && a.join(frames[0]);
            frames.push(a);
        }
        return frames;
    }

    /**
     * -------------------------------------------------------- carousel end -----------------------------------------------------------------------------------
     */

})();