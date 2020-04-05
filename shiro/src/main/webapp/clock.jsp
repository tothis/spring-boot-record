<%@ page pageEncoding="utf8" trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
    <meta charset="utf8">
    <title>clock</title>
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        body {
            background-color: #000;
        }

        ul, li {
            list-style: none;
        }

        ul {
            position: fixed;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
            margin: auto;
            background-color: #f40;
            width: 0px;
            height: 0px;
        }

        li {
            text-align: right;
            transform-origin: left 50%;
            position: absolute;
            left: 0;
            top: 50%;
            margin-top: -50%;
            transition: all ease .5s;
            color: #666;
            user-select: none;
        }

        .s li {
            width: 420px;
        }

        .m li {
            width: 350px;
        }

        .h li {
            width: 280px;
        }

        .sx li {
            width: 210px;
        }

        .d li {
            width: 170px;
        }

        .M li {
            width: 100px;
        }

        .Y li {
            left: 0%;
            transform: translate(-50%);
            word-break: keep-all;
            color: #fff;
        }
    </style>
</head>
<body>
<ul class="Y">
    <script>
        document.write('<li>' + new Date().getFullYear() + '年</li>')
    </script>
</ul>
<ul class="s"></ul>
<ul class="m"></ul>
<ul class="h"></ul>
<ul class="sx"></ul>
<ul class="d"></ul>
<ul class="M"></ul>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>
<script>
    let chnNumChar = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九']
    let chnUnitSection = ['', '万', '亿', '万亿', '亿亿']
    let chnUnitChar = ['', '十', '百', '千']
    let showNum;

    function sectionToChinese(section) {
        let strIns = '', chnStr = ''
        let unitPos = 0
        let zero = true
        while (section > 0) {
            let v = section % 10
            if (v === 0) {
                if (!zero) {
                    zero = true
                    chnStr = chnNumChar[v] + chnStr
                }
            } else {
                zero = false
                strIns = chnNumChar[v]
                strIns += chnUnitChar[unitPos]
                chnStr = strIns + chnStr
            }
            unitPos++
            section = Math.floor(section / 10)
        }
        return chnStr
    }

    function numberToChinese(num) {
        let unitPos = 0
        let strIns = '', chnStr = ''
        let needZero = false

        if (num === 0) {
            return chnNumChar[0]
        }

        while (num > 0) {
            let section = num % 10000;
            if (needZero) {
                chnStr = chnNumChar[0] + chnStr
            }
            strIns = sectionToChinese(section)
            strIns += (section !== 0) ? chnUnitSection[unitPos] : chnUnitSection[0]
            chnStr = strIns + chnStr
            needZero = (section < 1000) && (section > 0)
            num = Math.floor(num / 10000)
            unitPos++
        }
        return chnStr
    }

    function getDate(year, month) {
        return new Date(year, month, 0).getDate()
    }

    function rotate(selector, t) {
        t = +t
        let lis = $(selector)
        for (let i = 0; i < lis.length; i++) {
            let rotate = (lis.length - i + t)
            lis.eq(i).css('transform', 'rotate(' + rotate * 360 / lis.length + 'deg)')
            rotate % lis.length == 0 && lis.eq(i).css('color', '#fff').siblings().css('color', '#666')
        }
        // 归零
        if (t == lis.length - 1) {
            setTimeout(function () {
                lis.css('transition', 'unset')
                for (let i = 0; i < lis.length; i++) {
                    let rotate = lis.length - i - 1
                    lis.eq(i).css('transform', 'rotate(' + rotate * 360 / lis.length + 'deg)')
                    rotate % lis.length == 0 && lis.eq(i).css('color', '#fff').siblings().css('color', '#666')
                }
                setTimeout(function () {
                    lis.css('transition', 'all ease .5s')
                }, 200)
            }, 500)
        }
    }

    function init(selector, num, label, len) {
        num = +num
        for (let i = 0; i < len; i++) {
            showNum = label === '午' ? (i == 1 ? '上' : '下') : numberToChinese(i)
            showNum = label == '月' && i == 0 ? numberToChinese(len) : showNum
            showNum = label == '号' && i == 0 ? numberToChinese(len) : showNum
            showNum = label == '点' && i == 0 ? numberToChinese(len) : showNum
            let rotate = num == len - 1 ? len - i - 1 : len - i + num
            if (rotate % len == 0) {
                $(selector).append('<li style="color:#fff;transform: rotate(' + rotate * 360 / len + 'deg)">' + showNum + label + '</li>')
            } else {
                $(selector).append('<li style="transform: rotate(' + rotate * 360 / len + 'deg)">' + showNum + label + '</li>')
            }
        }
    }

    function getNow() {
        let date = new Date()
        let s = date.getSeconds()
        let m = date.getMinutes()
        let h = date.getHours()
        let sx = h < 12 ? 1 : 0
        h = h > 12 ? h - 12 : h
        let d = date.getDate()
        let M = date.getMonth() + 1
        let y = date.getFullYear()
        // 归零
        d = d == getDate(y, M) ? 0 : d
        M = M == '12' ? 0 : M
        h = h == '12' ? 0 : h
        return {s: s, m: m, h: h, d: d, M: M, sx: sx, y: y}
    }

    let start = getNow()
    init('.s', start.s, '秒', 60)
    init('.m', start.m, '分', 60)
    init('.h', start.h, '点', 12)
    init('.sx', start.sx, '午', 2)
    init('.d', start.d, '号', getDate(start.y, start.M))
    init('.M', start.M, '月', 12)

    setInterval(function () {
        let now = getNow()
        if (start.s != now.s) {
            rotate('.s li', now.s)
            start.s = now.s
        }
        if (start.m != now.m) {
            rotate('.m li', now.m)
            start.m = now.m
        }
        if (start.h != now.h) {
            rotate('.h li', now.h)
            start.h = now.h
        }
        if (start.sx != now.sx) {
            rotate('.sx li', now.sx)
            start.sx = now.sx
        }
        if (start.d != now.d) {
            rotate('.d li', now.d)
            start.d = now.d
        }
        if (start.M != now.M) {
            // 月份变更时更新日期
            setTimeout(function () {
                $('.d li').remove()
                init('.d', now.d, '号', getDate(now.y, now.M))
            }, 500)
            rotate('.M li', now.M)
            start.M = now.M
        }
        if (start.y != now.y) {
            $('.Y li').text(now.y + '年')
            start.y = now.y
        }
    }, 1000)
</script>
</body>
</html>
