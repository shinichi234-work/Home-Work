let sound; // Переменная где будет находится аудио-дорожка
let isInitialised; // Состояние, которое обозначает инициализированы ли значения или нет
let isLoaded = false;
let amplitude;
let amplitudes = [];

let fft;

function preload() {
    soundFormats('mp3', 'wav'); // Определяем аудио форматы, поддерживаемые плеером
    sound = loadSound('/assets/parsRadio_loop.mp3', () => {
        console.log("sound is loaded!"); // Загружаем музыку и при успешной загрузке выводим в консоль сообщение, что музыка загрузилась
        isLoaded = true;
    });
    isInitialised = false;
    sound.setVolume(0.2); // Устанавливаем громкость на 20%
}

function setup() {
    createCanvas(1024, 1024);
    textAlign(CENTER); // Центрируем следующий текст по центру
    textSize(32);

    amplitude = new p5.Amplitude();

    for (let i = 0; i < 512; i++)
        amplitudes.push(0);

    fft = new p5.FFT();
}

function draw() {
    background(0);
    fill(255);

    if (isInitialised && !sound.isPlaying()) {
        text("Press any key for play sound", width / 2, height / 2);
    } else if (sound.isPlaying()) {
        let level = amplitude.getLevel();
        amplitudes.push(level);
        amplitudes.shift();
        text(level, width / 2, 40);
        let size = map(level, 0, 0.20, 100, 200);
        ellipse(width / 2, height / 2, size, size);

        let freqs = fft.analyze();

        // Визуализатор на основе средних значений 5 частот
        let barWidth = width / (freqs.length / 5);
        stroke(0, 150, 0);
        for (let i = 2; i < freqs.length - 2; i += 5) {
            let avg = (freqs[i - 2] + freqs[i - 1] + freqs[i] + freqs[i + 1] + freqs[i + 2]) / 5;
            let barHeight = map(avg, 0, 255, 0, height / 2);
            fill(map(i, 0, freqs.length, 0, 255), 100, 150);
            rect((i / 5) * barWidth, height - barHeight, barWidth - 2, barHeight);
        }

        noStroke();

        let energy = fft.getEnergy("bass");
        fill("#FF0000");
        ellipse(width / 4, height / 2, 100 + energy);

        let high_energy = fft.getEnergy("highMid");

        fill("#0000FF");
        ellipse(width * 3 / 4, height / 2, 100 + high_energy);
    }
}

function keyPressed() {
    if (!isInitialised) {
        isInitialised = true;

        let r = 1//map(mouseX, 0, width, 0.5, 4.0); // r - скорость воспроизведения звука, которую мы рассчитываем в зависимости от положения мыши по x. Чем правее - тем быстрее запускается воспроизведение//
        if (isLoaded)
            sound.loop(0, r); // loop - функция для зацикливания. 0 - откуда начинается зацикливание по времени r - rate - playback rate
    } else {
        if (key == ' ') {
            if (sound.isPaused()) sound.play();
            else sound.pause();
        }
    }
}
