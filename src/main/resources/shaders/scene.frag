#version 330

in vec2 outTextCoord;
out vec4 fragColor;
uniform sampler2D txtSampler;

struct Material {
    vec4 diffuse;
};
uniform Material material;

void main() {
    fragColor = texture(txtSampler, outTextCoord) + material.diffuse;
}