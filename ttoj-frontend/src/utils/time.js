import dayjs from "dayjs";

export function millsToDate(mill, format='YYYY-MM-DD HH:mm:ss'){
    return dayjs(parseInt(mill)).format(format)
}

export function getDurationString(start, end){
    let duration = dayjs.duration(dayjs(end).diff(dayjs(start))).as('minutes')
    let hours = Math.trunc(duration / 60)
    let minutes = Math.trunc(duration % 60)
    return (hours > 0 ? hours + '小时' : '') + minutes + '分钟'
}

export function getDurationSeconds(start, end){
    let duration = dayjs.duration(dayjs(end).diff(dayjs(start))).as('seconds')
    return Math.trunc(duration)
}


export function getCountDown(remainSeconds){
    let hours = Math.trunc(remainSeconds / 3600)
    let minutes = Math.trunc((remainSeconds % 3600) / 60)
    let seconds =  remainSeconds % 60
    let countDown = hours + ':' + String(minutes).padStart(2, '0') + ':' + String(seconds).padStart(2, '0')
    return countDown
}

export default {
    millsToDate,
    getDurationString,
    getCountDown
}
