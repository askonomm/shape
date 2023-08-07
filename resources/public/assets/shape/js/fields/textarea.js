window.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('textarea.auto-size').forEach(el => {
        console.log(el.scrollHeight)
        el.style.height = '0px';
        el.style.height = el.scrollHeight + 'px';

        el.addEventListener('input', () => {
            el.style.height = '0px';
            el.style.height = el.scrollHeight + 'px';
        })
    });
})