#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;

uniform bool isRight;
uniform float angle;
uniform vec3 centre;
uniform float resolution;
uniform vec2 offset;
out vec2 texCoord;

vec3 rotate(vec3 point) {
    vec3 rotated;

    float shift_offset_x;
    float shift_offset_y = 0;
    float rad = radians(angle);
    float co = cos(rad);
    float si = sin(rad);

    if(isRight) {
        shift_offset_x = 0.0;
    } else {
        shift_offset_x = 0.0;
    }
    // shift
    point.x -= (centre.x + shift_offset_x);
    point.y -= (centre.y + shift_offset_y);

    // rotate
    rotated.x = point.x * co - point.y * si;
    rotated.y = point.x * si + point.y * co;

    // shift back
    rotated.x += (centre.x + shift_offset_x);
    rotated.y += (centre.y + shift_offset_y);

    return rotated;
}

void main() {
    vec3 position = rotate(aPos);
    gl_Position = vec4(position.x + offset.x, (position.y + offset.y) * resolution, position.z, 1.0);
    if (isRight)
        texCoord = aTexCoord;
    else
        texCoord = vec2(1.0 - aTexCoord.x, aTexCoord.y);
}